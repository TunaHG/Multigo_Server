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
		return vo;
	}
	
	/*
	 * Get Certain User's All Purchase History
	 * 특정 유저의 모든 구매내역 return
	 */
	public List<ListsVO> getAllList(String user_id){
		List<ListsVO> list = ldao.getAllList(user_id);
		return list;
	}
	
	/*
	 * Get Certain Purchase History's All ListsAndItems
	 * 특정 구매내역에 해당하는 모든 구매물품들 return
	 */
	public List<LaisVO> getAllLais(int list_id){
		List<LaisVO> list = lidao.getAllLais(list_id);
		return list;
	}
	
	/*
	 * Add Purchase History
	 * 처음 입장시 List(장바구니)생성
	 */
	public void addList(ListsVO vo) {
		ldao.addList(vo);
	}
	
	/*
	 * Update Purchase History
	 * 생성된 List에 Item을 추가하거나 제거할때 Update를 해서 총액을 갱신
	 */
	public void updateListTotal(ListsVO vo) {
		ldao.updateListTotal(vo);
	}
	
	/*
	 * Update Purchase Date in Receipt
	 * 퇴장시 현재 장바구니의 구매시간을 변경
	 */
	public void updateListDate(ListsVO vo) {
		ldao.updateListDate(vo);
	}
	
	/*
	 * Delete Purchase History
	 * 장바구니가 생성된 이후 Item을 구입하지 않은 상태로 퇴장하였을 때 장바구니 삭제
	 */
	public void deleteList(int list_id) {
		ldao.deleteList(list_id);
	}
	
	/*
	 * Add Purchase Item
	 * 장바구니에 Item이 추가될 때마다 세부내역 추가
	 */
	public void addLais(LaisVO vo) {
		lidao.addLais(vo);
	}
	
	/*
	 * Delete Purchase Item
	 * 장바구니에 담긴 Item을 제거
	 */
	public void deleteLais(LaisVO vo) {
		lidao.deleteLais(vo);
	}
	
	/*
	 * Update Purchase Item
	 * 장바구니에 담긴 Item의 개수(cnt)를 변경
	 */
	public void updateLais(LaisVO vo) {
		lidao.updateLais(vo);
	}
	
	/*
	 * Update Item's Stock in Market
	 * 사용자가 결제한 매장의 Item 재고를 Update
	 */
	public void updateStockInMarket(MarketsVO vo) {
		mdao.updateStockInMarket(vo);
	}
}
