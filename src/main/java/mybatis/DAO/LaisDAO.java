package mybatis.DAO;

import org.apache.ibatis.session.SqlSession;

import mybatis.VO.LaisVO;

public class LaisDAO {
	SqlSession session;
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	/*
	 * Insert New Item to the Reciept list
	 */
	public void addLais(LaisVO vo) {
		session.insert("inserLais", vo);
		System.out.println("[Server]\t[Lais]\tAdd Item to Receipt list");
	}
	
	/*
	 * Get Detail item list in Certain Receipt list
	 */
	public LaisVO getLais(int list_id) {
		LaisVO vo = session.selectOne("selectLais", list_id);
		System.out.println("[Server]\t[Lais]\tGet Detail List about Receipt");
		return vo;
	}
	
	/*
	 * Update Item's count in Certain Receipt list
	 */
	public void updateLais(LaisVO vo) {
		session.update("updateLais", vo);
		System.out.println("[Server]\t[Lais]\tUpdate Item's Count in Receipt list");
	}
	
	/*
	 * Delete Item in Receipt list
	 */
	public void deleteLais(LaisVO vo) {
		session.delete("deleteLais", vo);
		System.out.println("[Server]\t[Lais]\tDelete Item in Receipt");
	}
}
