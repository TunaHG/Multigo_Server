package mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedObject {
	List<CommandRunnable> clients = new ArrayList<>();
	Map<Integer, List<CommandRunnable>> map = new HashMap<>();
	
	// {0, } : 연결된, 매장에 입장하지 않은 Client들과 Market
	// {1, } : 1번 매장에 입장한 Client들
	// {2, } : 2번 매장에 입장한 Client들
}
