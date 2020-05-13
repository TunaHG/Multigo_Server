package mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedObject {
	// Server에 접속중인 모든 Client를 의미(Market도 포함되어 있음)
	List<CommandRunnable> clients = new ArrayList<>();
	
	// 특정 Market을 이용중인 모든 Client들의 List를 의미하는 Map
	Map<Integer, List<CommandRunnable>> map = new HashMap<>();
	// {0, } : 연결된, 매장에 입장하지 않은 Client들과 Market
	// {1, } : 1번 매장에 입장한 Client들
	// {2, } : 2번 매장에 입장한 Client들
	
	// 로그인 시, 중복로그인인지를 판별
	// 이미 Server에 해당 ID를 가진 Client가 존재한다면 중복로그인
	public boolean duplicateDetection(String user_id) {
		// Runnable객체에 함수를 사용할 때 결과값으로 전송할 변수
		boolean result = false;
		// Server에 접속중인 모든 Clients의 ID를 탐색
		for(CommandRunnable client : clients) {
			if(client.getId() != null && client.getId().equals(user_id)) {
				// 중복되는 ID가 존재한다면 결과값을 변경
				result = true;
				break;
			}
		}
		// 결과값 반환
		return result;
	}
	
	// Client가 특정 Market으로 입장시, Map을 갱신하는 method
	public void Enter(int market_id, String user_id) {
		List<CommandRunnable> list = map.get(market_id);
		// list가 null이라면 ArrayList를 생성하고 진행
		if(list == null)
			list = new ArrayList<>();
		
		// Server에 접속중인 모든 Client중 id를 이용하여 탐색
		for(CommandRunnable client : clients) {
			if(client.getId() != null && client.getId().equals(user_id)) {
				list.add(client);
				// Client에게 입장하였다는 신호를 전송
				client.getPw().println("@@Enter");
				client.getPw().flush();
				break;
			}
		}
		// map 갱신
		map.put(market_id, list);
	}
	
	// Market에서 퇴장시, map에서 해당 Market를 이용중인 Client list중
	// 해당 CLient를 제거하는 method
	public void Exit(int market_id, String user_id) {
		// 해당 Market을 이용중인 모든 Client들의 List
		List<CommandRunnable> list = map.get(market_id);
		
		// List를 탐색하여 해당 Client를 제거하는 작업을 진행
		for(CommandRunnable client : list) {
			if(client.getId() != null && client.getId().equals(user_id)) {
				list.remove(client);
				// 제거하며 해당 Client에게 퇴장하였다는 신호를 전송
				client.getPw().println("@@Exit");
				client.getPw().flush();
				break;
			}
		}
		// map의 value값 갱신
		map.put(market_id, list);
	}
	
	// 앱 종료시, Client에서 해당 Client를 제거하는 method
	public void Terminate(String user_id) {
		for(CommandRunnable client : clients) {
			if(client.getId() != null && client.getId().equals(user_id)) {
				clients.remove(client);
				break;
			}
		}
	}
}
