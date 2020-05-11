package mybatis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {

	public static void main(String[] args) {
		ServerSocket server;
		Socket socket;
		SharedObject shared = new SharedObject();
		ExecutorService es = Executors.newCachedThreadPool();
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory;
		SqlSession session;

		try {
			factory = builder.build(Resources.getResourceAsReader("mybatis/mybatis-config.xml"));
			session = factory.openSession(true);
			
			server = new ServerSocket(6020);
			
			while (true) {
				socket = server.accept();
				
				CommandRunnable runnable = new CommandRunnable(socket, shared, session);
				
				shared.clients.add(runnable);
				
				es.execute(runnable);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}