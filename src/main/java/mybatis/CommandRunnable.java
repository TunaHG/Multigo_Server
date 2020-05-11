package mybatis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.ibatis.session.SqlSession;

public class CommandRunnable implements Runnable {
	private Socket socket;
	private SharedObject shared;
	private BufferedReader br;
	private PrintWriter pw;
	private Service service = new Service();
	
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
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
