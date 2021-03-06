package mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import mybatis.DAO.ItemsDAO;
import mybatis.DAO.LaisDAO;
import mybatis.DAO.ListsDAO;
import mybatis.DAO.MarketsDAO;
import mybatis.VO.ItemsVO;
import mybatis.VO.LaisVO;
import mybatis.VO.ListsVO;
import mybatis.VO.MarketsVO;

public class Service {
	ItemsDAO idao = new ItemsDAO();
	MarketsDAO mdao = new MarketsDAO();
	ListsDAO ldao = new ListsDAO();
	LaisDAO lidao = new LaisDAO();
	
	public void setSession(SqlSession session) {
		idao.setSession(session);
		mdao.setSession(session);
		ldao.setSession(session);
		lidao.setSession(session);
	}
	
	/*
	 * Get Data(Name, Price) about Certain Item
	 * Item의 QR코드값이 들어왔을때 어떤 Item인지 return
	 */
	public ItemsVO getItem(String item_id) {
		ItemsVO vo = idao.getItem(item_id);
		System.out.println("[Service]\tGet Item Data");
		return vo;
	}
	
	/*
	 * Get Certain User's All Purchase History
	 * 특정 유저의 모든 구매내역 return
	 */
	public List<ListsVO> getAllList(String user_id){
		List<ListsVO> list = ldao.getAllList(user_id);
		System.out.println("[Service]\tGet user's all purchase history");
		return list;
	}
	
	/*
	 * Get Certain Purchase History's All ListsAndItems
	 * 특정 구매내역에 해당하는 모든 구매물품들 return
	 */
	public List<LaisVO> getAllLais(int list_id){
		List<LaisVO> list = lidao.getAllLais(list_id);
		System.out.println("[Service]\tGet history's all items");
		return list;
	}
	
	/*
	 * Add Purchase History
	 * 퇴장시 결제를 진행하며 구매내역 List 생성
	 */
	public int addList(ListsVO vo) {
		int result = ldao.addList(vo);
		System.out.println("[Service]\tAdd Purchase History");
		return result;
	}
	
	/*
	 * Add Purchase Item
	 * Data로 넘어온 구매물품들을 추가
	 */
	public void addLais(LaisVO vo) {
		lidao.addLais(vo);
		System.out.println("[Service]\tAdd Purchase Item");
	}
	
	/*
	 * Update Item's Stock in Market
	 * 사용자가 결제한 매장의 Item 재고를 Update
	 */
	public void updateStockInMarket(MarketsVO vo) {
		mdao.updateStockInMarket(vo);
		System.out.println("[Service]\tUpdate Item's stock");
	}
}
