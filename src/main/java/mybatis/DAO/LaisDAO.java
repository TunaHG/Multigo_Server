package mybatis.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import mybatis.VO.LaisVO;

public class LaisDAO {
	SqlSession session;
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	/*
	 * # Included in Service
	 * Insert New Item to the Reciept list
	 * 사용자가 장바구니에 아이템을 담았을 때 사용하는 method
	 */
	public void addLais(LaisVO vo) {
		// Sql: INSERT INTO LISTS_AND_ITEMS VALUES(#{list_id}, #{item_id}, #{cnt})
		session.insert("inserLais", vo);
		System.out.println("[Server]\t[LaisDAO]\tAdd Item to Receipt list");
	}
	
	/*
	 * # Included in Service
	 * Get Detail item list in Certain Receipt list
	 * 사용자가 구매내역을 조회할 때 해당 구매내역에 해당하는 모든 구매물품을 조회하는 method
	 */
	public List<LaisVO> getAllLais(int list_id) {
		// Sql: SELECT ITEM_ID, CNT FROM LISTS_AND_ITEMS WHERE LIST_ID = #{list_id}
		List<LaisVO> vo = session.selectList("selectLais", list_id);
		System.out.println("[Server]\t[LaisDAO]\tGet Detail List about Receipt");
		return vo;
	}
	
	/*
	 * # Included in Service
	 * Update Item's count in Certain Receipt list
	 * 사용자가 장바구니에 담긴 Item의 개수를 조정할 때 사용하는 method
	 */
	public void updateLais(LaisVO vo) {
		// Sql: UPDATE LISTS_AND_ITEMS SET CNT = #{cnt} WHERE LIST_ID = #{list_id} AND ITEM_ID = #{item_id}
		session.update("updateLais", vo);
		System.out.println("[Server]\t[LaisDAO]\tUpdate Item's Count in Receipt list");
	}
	
	/*
	 * # Included in Service
	 * Delete Item in Receipt list
	 * 사용자가 장바구니에 담긴 Item을 제거했을 때 사용하는 method
	 */
	public void deleteLais(LaisVO vo) {
		// Sql: DELETE LISTS_AND_ITEMS WHERE LIST_ID = #{list_id} AND ITEM_ID = #{item_id}
		session.delete("deleteLais", vo);
		System.out.println("[Server]\t[LaisDAO]\tDelete Item in Receipt");
	}
}
