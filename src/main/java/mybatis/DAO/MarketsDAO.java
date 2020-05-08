package mybatis.DAO;

import org.apache.ibatis.session.SqlSession;

import mybatis.VO.MarketsVO;

/*
 * Market Table
 */
public class MarketsDAO {
	// Mybatis Session
	SqlSession session;

	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	/*
	 * # Not included in Service
	 * Insert New Item to Market
	 * 매장에 새로운 Item이 추가될 때 사용하는 method
	 * 사용자가 아닌 관리자가 사용하는 method
	 */
	public void addNewItem(MarketsVO vo) {
		// Sql: INSERT INTO MARKETS VALUES(#{market_id}, #{item_id}, #{stock})
		session.insert("insertNewItem", vo);
		System.out.println("[Server]\t[MarketsDAO]\tNew Item Added");
	}
	
	/*
	 * # Not included in Service
	 * Get Current Stock of Item in Market
	 * 현재 매장 내 존재하는 특정 Item의 재고를 확인하는 method
	 * 사용자가 아닌 관리자가 사용하는 method
	 */
	public int getCurrentStockInMarket(MarketsVO vo) {
		// Sql: SELECT STOCK FROM MARKETS WHERE MARKET_ID = #{market_id} AND ITEM_ID = #{item_id}
		int stock = session.selectOne("currentStockInMarket", vo);
		System.out.println("[Server]\t[MarketsDAO]\tGet Current Stock");
		return stock;
	}
	
	/*
	 * # Included in Service
	 * Update Item Stock in Market
	 * 사용자가 Item을 장바구니에 넣거나, 결제했을 경우
	 * 해당 매장에 존재하는 Item의 재고 개수를 Update하는 경우 사용하는 method
	 */
	public void updateStockInMarket(MarketsVO vo) {
		// Sql: UPDATE MARKETS SET STOCK = #{stock} WHERE MARKET_ID = #{market_id} AND ITEM_ID = #{item_id}
		session.update("updateStockInMarket", vo);
		System.out.println("[Server]\t[MarketsDAO]\tUpdate Stock");
	}
	
	/*
	 * # Not included in Service
	 * Delete Item in Market
	 * 현재 매장에 존재하는 Item을 삭제하는 method
	 * 사용자가 아닌 관리자가 사용하는 method
	 */
	public void deleteItemInMarket(MarketsVO vo) {
		// Sql: DELETE MARKETS WHERE MARKET_ID = #{market_id} AND ITEM_ID = #{item_id}
		session.delete("deleteItemInMarket", vo);
		System.out.println("[Server]\t[MarketsDAO]\tDelete Item");
	}
}
