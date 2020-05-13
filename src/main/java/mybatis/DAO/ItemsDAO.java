package mybatis.DAO;

import org.apache.ibatis.session.SqlSession;

import mybatis.VO.ItemsVO;

public class ItemsDAO {
	SqlSession session;
	public void setSession(SqlSession session) {
		this.session = session;
	}	
	
	/*
	 * # Not included in Service
	 * Insert New Item Method
	 * 사용자의 요청으로 발생하는 method가 아님
	 * 관리자가 Item을 추가하는 경우 사용하는 method
	 */
	public void addItem(ItemsVO vo) {
		// Sql: INSERT INTO ITEMS VALUES(#{item_id}, #{name}, #{price})
		session.insert("insertItem", vo);
		System.out.println("[ItemDAO]\tInsert New Item");
	}
	
	/*
	 * # Included in Service
	 * Get Item Data Method
	 * 사용자의 요청으로 Item에 대한 QR코드값이 들어왔을 때
	 * 해당 QR코드값에 해당하는 Item의 정보를 반환하는 method
	 */
	public ItemsVO getItem(String item_id) {
		// Sql: SELECT * FROM ITEMS WHERE ITEM_ID = #{item_id}
		ItemsVO vo = session.selectOne("selectItem", item_id);
		System.out.println("[ItemDAO]\tGet Item");
		return vo;
	}
	
	/*
	 * # Not included in Service
	 * Update Item Data Method
	 * 사용자의 요청으로 발생하는 method가 아님
	 * 관리자가 특정 Item의 이름 혹은 가격을 수정할 경우 사용하는 method
	 */
	public void updateItem(ItemsVO vo) {
		// Sql: UPDATE ITEMS SET NAME = #{name}, PRICE = #{price} WHERE ITEM_ID = #{item_id}
		session.update("updateItem", vo);
		System.out.println("[ItemDAO]\tUpdate Item");
	}
	
	/*
	 * # Not included in Service
	 * Delete Item Method
	 * 사용자의 요청으로 발생하는 method가 아님
	 * 관리자가 특정 Item의 정보를 삭제할 경우 사용하는 method
	 */
	public void deleteItem(ItemsVO vo) {
		// Sql: DELETE ITEMS WHERE ITEM_ID = #{item_id}
		session.delete("deleteItem", vo);
		System.out.println("[ItemDAO]\tDelete Item");
	}
}
