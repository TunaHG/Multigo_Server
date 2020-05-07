package mybatis;

import org.apache.ibatis.session.SqlSession;

public class ItemsDAO {
	SqlSession session;
	public void setSession(SqlSession session) {
		this.session = session;
	}	
	
	public void addItem(ItemsVO vo) {
		session.insert("insertitem", vo);
	}
	
	public ItemsVO getItem(String item_id) {
		ItemsVO vo = session.selectOne("selectitem", item_id);
		return vo;
	}
	
	public void updateItem(ItemsVO vo) {
		session.update("updateitem", vo);
	}
	
	public void deleteItem(ItemsVO vo) {
		session.delete("deleteitem", vo);
	}
}
