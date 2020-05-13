	package mybatis.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import mybatis.VO.ListsVO;

public class ListsDAO {
	SqlSession session;
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	/*
	 * # Included in Service
	 * Insert New Receipt method
	 * 사용자가 장바구니를 결제할 시 구매내역을 생성하는 method
	 */
	public int addList(ListsVO vo) {
		// Sql: INSERT INTO LISTS VALUES(LIST_SEQUENCE.NEXTVAL, SYSDATE, #{total}, #{user_id})
		// SelectKey: SELECT LIST_SEQUENCE.CURRVAL FROM DUAL
		int result = session.insert("insertList", vo);
		System.out.println("[ListsDAO]\tAdd new list");
		return result;
	}
	
	/*
	 * # Included in Service
	 * Get List_Sequence's Current value
	 * List가 새로 등록되었을 때, 해당 List_id를 가져오기 위하여 사용하는 method
	 */
	public int getSeqVal() {
		// Sql: SELECT LIST_SEQUENCE.CURRVAL FROM DUAL;
		int result = session.selectOne("selectSeqVal");
		System.out.println("[ListsDAO]\tGet List_seq Current value");
		return result;
	}
	
	/*
	 * # Included in Service
	 * Get Certain User's All Receipt
	 * 특정 사용자의 모든 구매내역을 반환하는 method
	 */
	public List<ListsVO> getAllList(String user_id){
		// Sql: SELECT * FROM LISTS WHERE USER_ID = #{user_id}
		List<ListsVO> list = session.selectList("selectAllList", user_id);
		System.out.println("[ListsDAO]\tGet User's All Receipt");
		return list;
	}
	
	/*
	 * # Not Included in Service
	 * Update Receipt's Total
	 * 사용자의 장바구니 내용 변경으로 인한 총액 수정에 사용하는 method
	 */
//	public void updateListTotal(ListsVO vo) {
//		// Sql: UPDATE LISTS SET TOTAL = #{total} WHERE LIST_ID = #{list_id}
//		session.update("updateListTotal", vo);
//		System.out.println("[ListsDAO]\tUpdate List's Total");
//	}
	
	/*
	 * # Not Included in Service
	 * Update Receipt's Purchase Date
	 * 사용자의 퇴장으로 인해 결제시 구매시간을 변경하는 경우 사용하는 method
	 */
//	public void updateListDate(ListsVO vo) {
//		// Sql: UPDATE LISTS SET PURCHASE_DATE = #{purchase_date} WHERE LIST_ID = #{list_id}
//		session.update("updateListDate", vo);
//		System.out.println("[ListsDAO]\tUpdate List's Purchase Date");
//	}
	
	/*
	 * # Not Included in Service
	 * Delete Receipt method
	 * Warning : Lais에서 해당 list_id를 가진 모든 정보를 먼저 삭제해야함
	 * 사용자가 빈 장바구니를 가지고 퇴장하였을 때 사용하는 method
	 * 현재 존재하는 장바구니를 삭제
	 */
//	public void deleteList(int list_id) {
//		// Sql: DELETE LISTS WHERE LIST_ID = #{list_id}
//		session.delete("deleteList", list_id);
//		System.out.println("[ListsDAO]\tDelete List");
//	}
}
