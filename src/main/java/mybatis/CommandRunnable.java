package mybatis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mybatis.VO.ItemsVO;
import mybatis.VO.LaisVO;
import mybatis.VO.ListsVO;

public class CommandRunnable implements Runnable {
	private String id;
	private int list_id;
	private Socket socket;
	private SharedObject shared;
	private BufferedReader br;
	private PrintWriter pw;
	private Service service = new Service();
	
	public String getId() {
		return id;
	}
	
	public int getList_id() {
		return list_id;
	}
	
	public PrintWriter getPw() {
		return pw;
	}
	
	CommandRunnable(String id) {
		this.id = id;
	}

	CommandRunnable(Socket socket, SharedObject shared, SqlSession session){
		this.socket = socket;
		this.shared = shared;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		service.setSession(session);
	}
	
	@Override
	public void run() {
		String command = "";
		try {
			while((command = br.readLine()) != null) {
				// CLient가 접속시 전송하는 Command
				// 입장 QR을 안띄워줄것인지, 로그인이 불가능하도록 할것인지는 선택
				if(command.startsWith("@@SetID")) {
					// Command: @@SetId [user_id]
					// 명령어 뒤에 같이오는 user_id를 입력
					String id = command.replace("@@SetID ", "");
					// 공용객체 method를 활용하여 Server에 접속중인 Clients를 탐색
					// 동일한 ID를 가진 Client가 존재한다면 true
					boolean detect = shared.duplicateDetection(id);
					// 동일한 ID가 이미 접속중이라면,
					if(detect) {
						// 중복되었다는 Message를 다시 Client에게 전송
						pw.println("@@Duplicate");
						pw.flush();
					} else {
						// 현재 Runnable객체의 id값을 user_id로 변경
						this.id = id;
						// 중복되지 않았고 접속했다는 Message를 전송
						pw.println("@@SetID " + this.id);
						pw.flush();
					}
				}
				// Client가 Market에 입장 시, Market이 전송하는 Command
				if(command.startsWith("@@Enter")) {
					// Command: @@Enter [market_id] [user_id]
					String[] cmd = command.split(" ");
					// Command에서 각 내용을 추출
					int market_id = Integer.parseInt(cmd[1]);
					String user_id = cmd[2];
					
					// 공용객체의 method를 이용하여 map을 갱신
					shared.Enter(market_id, user_id);
				}
				// Client가 특정 Item의 정보를 요청하는 Command
				if(command.startsWith("@@GetItem")) {
					// Command: @@GetItem [item_id]
					String item_id = command.replace("@@GetItem ", "");
					// item_id를 이용하여 DB에서 존재하는 item Data를 가져옴
					ItemsVO vo = service.getItem(item_id);
					// Item Data가 담긴 VO객체를 JSON으로 변환
					String result = new ObjectMapper().writeValueAsString(vo);
					// JSON Data Check
					System.out.println(result);
					// 변환된 JSON을 CLient에게 전송
					pw.println("@@GetItem " + result);
					pw.flush();
				}
				// Client가 본인의 모든 이전 구매내역을 요청하는 Command
				if(command.startsWith("@@GetAllList")) {
					// Command: @@GetAllList [user_id]
					String user_id = command.replace("@@GetAllList ", "");
					// user_id를 이용하여 해당  user가 이전에 구매한 모든 구매내역을 가져옴
					List<ListsVO> list = service.getAllList(user_id);
					// 가져온 모든 구매내역을 JSON으로 변환
					String result = new ObjectMapper().writeValueAsString(list);
					// JSON Data Check
					System.out.println(result);
					// 변환된 JSON을 Client에게 전송
					pw.println("@@GetAllList " + result);
					pw.flush();
				}
				// Client가 특정 구매내역에 대한 상세내역을 요청하는 Command
				if(command.startsWith("@@GetAllLais")) {
					// Command: @@GetAllLais [list_id]
					String tmpList_id = command.replace("@@GetAllLais", "");
					int list_id = Integer.parseInt(tmpList_id);
					// list_id를 이용하여 해당 list에 속하는 item_id와 수량을 가져옴
					List<LaisVO> list = service.getAllLais(list_id);
					// 가져온 상세내역을 JSON으로 변환
					String result = new ObjectMapper().writeValueAsString(list);
					// JSON Data Check
					System.out.println(result);
					// 변환된 JSON을 Client에게 전송
					pw.println("@@GetAllLais " + result);
					pw.flush();
				}
				// Client가 퇴장 시, 장바구니를 이용하여 구매내역 생성을 요청하는 method
				if(command.startsWith("@@AddList")) {
					// Command: @@AddList [total]
					String tmpTotal = command.replace("@@AddList ", "");
					int total = Integer.parseInt(tmpTotal);
					// list_id: sequence로 자동생성
					// purchase_date: sysdate로 자동생성
					// user_id: 현재 Runnable객체가 가지고 있음
					// 가져온 Data를 활용하여 ListsVO객체 생성
					ListsVO vo = new ListsVO();
					vo.setUser_id(this.id);
					vo.setTotal(total);
					// ListsVO객체를 DB로 전송하여 Table에 입력
					service.addList(vo);
					
					// 생성된 list_id를 가져와서 객체에 입력
					// Lais들을 추가할 때 list_id가 필요한데, 그곳에 사용하기 위함
					this.list_id = service.getSeqVal();
				}
				// Client가 퇴장 시, 구매내역을 생성한 이후 상세내역 생성을 요청하는 method
				if(command.startsWith("@@AddLais")) {
					// Command: @@AddLais [Json Data of Multiple LaisVO]
					String jsonData = command.replace("@@AddLais ", "");
					// 가져온 JSON Data를 VO를 활용한 List객체로 변환
					List<LaisVO> list = new ObjectMapper().readValue(jsonData, new TypeReference<List<LaisVO>>() {});
					
					for(LaisVO vo : list) {
						// 각 VO마다 DB로 전송하여 Table에 입력
						vo.setList_id(this.list_id);
						service.addLais(vo);
					}
				}
				// Client가 Market에서 퇴장 시, Market이 전송하는 Command
				if(command.startsWith("@@Exit")) {
					// Command: @@Exit [market_id] [user_id]
					String[] cmd = command.split(" ");
					int market_id = Integer.parseInt(cmd[1]);
					String user_id = cmd[2];
					
					// 공용객체의 method를 이용해서 map을 갱신
					shared.Exit(market_id, user_id);
				}
				// Client가 앱을 종료하여 Server와의 연결을 종료할 때 전송하는 Command
				if(command.startsWith("@@Terminate")) {
					// 공용객체의 method를 이용
					// Server에 접속중인 모든 Client를 의미하는 clients에서 제거
					shared.Terminate(id);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null)
					br.close();
				if(pw != null)
					pw.close();
				if(socket != null)
					socket.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

}
