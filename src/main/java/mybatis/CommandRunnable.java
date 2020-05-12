package mybatis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import mybatis.VO.ItemsVO;

public class CommandRunnable implements Runnable {
	private String id;
	private Socket socket;
	private SharedObject shared;
	private BufferedReader br;
	private PrintWriter pw;
	private Service service = new Service();
	
	public String getId() {
		return id;
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
				if(command.startsWith("@@SetID")) {
					String id = command.replace("@@SetID ", "");
					this.id = id;
					pw.println("@@SetID " + this.id);
					System.out.println("[Main]\t" + id);
				}
				if(command.startsWith("@@Enter")) {
					System.out.println("[Command] " + command);
					String[] cmd = command.split(" ");
					int market_id = Integer.parseInt(cmd[1]);
					List<CommandRunnable> list = shared.map.get(market_id);
					if(list == null)
						list = new ArrayList<>();
					
					System.out.println(shared.clients.size());
					for(CommandRunnable client : shared.clients) {
						if(client.id != null && client.id.equals(cmd[2])) {
							System.out.println(client.id);
							list.add(client);
							client.getPw().println("@@Enter");
							client.getPw().flush();
						}
					}
					System.out.println("[Main]\tPush Client Msg");
					shared.map.put(market_id, list);
				}
				if(command.startsWith("@@GetItem")) {
					String item_id = command.replace("@@GetItem ", "");
					ItemsVO vo = service.getItem(item_id);
					String result = new ObjectMapper().writeValueAsString(vo);
					System.out.println(result);
				}
				if(command.startsWith("@@GetAllList")) {
					
				}
				if(command.startsWith("@@GetAllLais")) {
					
				}
				if(command.startsWith("@@AddLais")) {
					
				}
				if(command.startsWith("@@Exit")) {
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
