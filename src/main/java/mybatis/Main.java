package mybatis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import mybatis.DAO.ItemsDAO;
import mybatis.VO.ItemsVO;

public class Main {
	static BufferedReader br;
	static ItemsDAO dao;
	public static void main(String[] args) throws Exception {
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(Resources.getResourceAsReader("mybatis/mybatis-config.xml"));
		SqlSession session = factory.openSession(true);

		dao = new ItemsDAO();
		dao.setSession(session);
		
		br = new BufferedReader(new InputStreamReader(System.in));
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				String command;
				try {
					while((command = br.readLine()) != null) {
						if(command.startsWith("@@gI")) {
							ItemsVO vo = dao.getItem("ITEM001");
							System.out.println(vo.getName());
							System.out.println(vo.getPrice());
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	
}
