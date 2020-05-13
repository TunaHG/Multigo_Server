package mybatis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class testClient {
	static BufferedReader br;
	static BufferedReader br2;
	static PrintWriter pw;
	static Socket socket;
	public static void main(String[] args) {
		try {
			socket = new Socket("localhost", 6020);
			br = new BufferedReader(new InputStreamReader(System.in));
			br2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				String line = "";
				try {
					pw.println("@@SetID USER001");
					pw.flush();
					while((line = br.readLine()) != null) {
						if(line.equals("@@Exit")) {
							pw.println("@@Exit 1 USER001");
							pw.flush();
							break;
						}
						pw.println(line);
						pw.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				String line = "";
				try {
					while((line = br2.readLine()) != null) {
						System.out.println(line);
						if(line.equals("@@Exit")) {
							br.close();
							br2.close();
							pw.close();
							socket.close();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t2.start();
	}

}
