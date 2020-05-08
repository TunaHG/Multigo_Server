package mybatis.DAO;

import org.apache.ibatis.session.SqlSession;

import mybatis.VO.ItemsVO;

public class ItemsDAO {
	SqlSession session;
	public void setSession(SqlSession session) {
		this.session = session;
	}	
	
	/*
	 * Insert New Item Method
	 */
	public void addItem(ItemsVO vo) {
		session.insert("insertItem", vo);
		System.out.println("[Server]\t[Item]\tInsert New Item");
	}
	
	/*
	 * Get Item Data Method
	 */
	public ItemsVO getItem(String item_id) {
		ItemsVO vo = session.selectOne("selectItem", item_id);
		System.out.println("[Server]\t[Item]\tGet Item");
		return vo;
	}
	
	/*
	 * Update Item Data Method
	 */
	public void updateItem(ItemsVO vo) {
		session.update("updateItem", vo);
		System.out.println("[Server]\t[Item]\tUpdate Item");
	}
	
	/*
	 * Delete Item Method
	 */
	public void deleteItem(ItemsVO vo) {
		session.delete("deleteItem", vo);
		System.out.println("[Server]\t[Item]\tDelete Item");
	}
}
