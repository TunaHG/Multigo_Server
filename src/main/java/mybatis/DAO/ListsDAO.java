package mybatis.DAO;

import org.apache.ibatis.session.SqlSession;

import mybatis.VO.ListsVO;

public class ListsDAO {
	SqlSession session;
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	public void addList(ListsVO vo) {
		session.insert("inserList", vo);
		System.out.println("[Server]\t[Lists]\tAdd new list");
	}
	
	public ListsVO getList(int list_id) {
		ListsVO vo = session.selectOne("selectList", list_id);
		System.out.println("[Server]\t[Lists]\tGet List");
		return vo;
	}
	
	public void updateList(ListsVO vo) {
		session.update("updateList", vo);
		System.out.println("[Server]\t[Lists]\tUpdate List");
	}
	
	public void deleteList(int list_id) {
		session.delete("deleteList", list_id);
		System.out.println("[Server]\t[Lists]\tDelete List");
	}
}
