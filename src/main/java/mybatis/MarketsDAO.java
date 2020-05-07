package mybatis;

import org.apache.ibatis.session.SqlSession;

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
	 * Insert New Item to Market
	 */
	public void addNewItem(MarketsVO vo) {
		session.insert("insertNewItem", vo);
		System.out.println("[Server]\t[Markets]\tNew Item Added");
	}
	
	/*
	 * Get Current Item Stock in Market
	 */
	public int getCurrentStockInMarket(MarketsVO vo) {
		int stock = session.selectOne("currentStockInMarket", vo);
		System.out.println("[Server]\t[Markets]\tGet Current Stock");
		return stock;
	}
	
	/*
	 * Update Item Stock in Market
	 */
	public void updateStockInMarket(MarketsVO vo) {
		session.update("updateStockInMarket", vo);
		System.out.println("[Server]\t[Markets]\tUpdate Stock");
	}
	
	/*
	 * Delete Item in Market
	 */
	public void deleteItemInMarket(MarketsVO vo) {
		session.delete("deleteItemInMarket", vo);
		System.out.println("[Server]\t[Markets]\tDelete Item");
	}
}
