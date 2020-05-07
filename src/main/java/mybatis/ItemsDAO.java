package mybatis;

import org.apache.ibatis.session.SqlSession;

public class ItemsDAO {
	SqlSession session;
	public void setSession(SqlSession session) {
		this.session = session;
	}	
	
	public void addItem(ItemsVO vo) {
		session.insert("insertItem", vo);
	}
	
	public ItemsVO getItem(String item_id) {
		ItemsVO vo = session.selectOne("selectItem", item_id);
		return vo;
	}
	
	public void updateItem(ItemsVO vo) {
		session.update("updateItem", vo);
	}
	
	public void deleteItem(ItemsVO vo) {
		session.delete("deleteItem", vo);
	}
}
