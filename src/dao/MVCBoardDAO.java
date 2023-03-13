package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dto.MemberOrder;
import dto.ProductDTO;
import dto.ReviewStarDTO;
import mvc.controller.JDBConnect;

public class MVCBoardDAO extends JDBConnect{

	public MVCBoardDAO() {
		super();
	}

	public int insertWrite(MemberOrder memo) {
		
		System.out.println("������� ������Ʈ ����!");
		int Result = 0;

		String query = "insert into mem_order( "
				+ " id,addressee,name,memberAddr,p_id,orderId) "
				+ " values( "
				+ " ?,?,?,?,?,?)";

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, memo.getId());
			psmt.setString(2, memo.getAddressee());
			psmt.setString(3, memo.getName());
			psmt.setString(4, memo.getMemberAddr());
			psmt.setString(5, memo.getP_id());
			psmt.setString(6, memo.getOrderId());
		
			Result = psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("������� ������Ʈ �� �����߻�");
			e.printStackTrace();
		}
		
		System.out.println("������� ������Ʈ ����!");
		return Result;

	}
	
	public MemberOrder selectView(String orderId) { 
		MemberOrder memo  = new MemberOrder();	
		String sql = "SELECT * FROM mem_order ";
		sql += " WHERE orderId = ? ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, orderId);
			rs = psmt.executeQuery();
			if(rs.next()) {
				memo.setOrderId(rs.getString("orderId"));
				memo.setAddressee(rs.getString("addressee"));
				memo.setId(rs.getString("id"));
				memo.setMemberAddr(rs.getString("memberAddr"));
				memo.setMemberAddr(rs.getString("p_id"));
				memo.setName(rs.getString("name"));	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return memo;		
	}
	
	public ProductDTO pSelectView(String id) { 
		ProductDTO dto  = new ProductDTO();	
		String sql = "SELECT * FROM product ";
		sql += " WHERE p_id = ? ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs.next()) {
				dto.setP_id(rs.getString("p_id"));
				dto.setP_name(rs.getString("p_name"));
				dto.setP_unitPrice(rs.getString("p_unitPrice"));
				dto.setP_category(rs.getString("p_category"));
				dto.setP_manufacturer(rs.getString("p_manufacturer"));
				dto.setP_fileName(rs.getString("p_filename"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;		
	}

	public List<ProductDTO> SelectSizeView(String id) { 
		List<ProductDTO> list = new ArrayList<>();
		String sql = "SELECT * FROM psize where s_id=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				ProductDTO dto = new ProductDTO();	
				dto.setP_size(rs.getString("s_size"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;		
	}
	
	public List<ProductDTO> SelectColorView(String id) { 
		List<ProductDTO> list = new ArrayList<>();
		String sql = "SELECT * FROM pcolor where c_id=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				ProductDTO dto = new ProductDTO();	
				dto.setP_color(rs.getString("c_color"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;		
	}

	public List<ReviewStarDTO> SelectReview(String id){
		String sql="select * from star_review where p_id=? order by `index` desc"; 
		List<ReviewStarDTO> reviewList = new ArrayList<>();
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				ReviewStarDTO reviewstar = new ReviewStarDTO();
				reviewstar.setM_id(rs.getString("m_id"));
				reviewstar.setP_id(rs.getString("p_id"));
				reviewstar.setContent(rs.getString("content"));
				reviewstar.setStar(rs.getString("star"));
				reviewstar.setIndex(rs.getString("index"));
				reviewList.add(reviewstar);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reviewList;
		
	}
	
	public String SelectCountReview(String id){
		String count="";
		String sql="select count(p_id) cnt from star_review where p_id=?" ; 
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				count=rs.getString("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
		
	}
	
	public List<ReviewStarDTO> SelectStarReView(String p_id,Map<String, Object> map){
		String sql="select * from star_review where p_id=? order by `index` desc limit ?,5"; 
		List<ReviewStarDTO> reviewList = new ArrayList<>();
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, p_id);
			psmt.setInt(2, (int) map.get("start")-1);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				ReviewStarDTO reviewstar = new ReviewStarDTO();
				reviewstar.setM_id(rs.getString("m_id"));
				reviewstar.setP_id(rs.getString("p_id"));
				reviewstar.setContent(rs.getString("content"));
				reviewstar.setStar(rs.getString("star"));
				reviewstar.setPostdate_ymd(rs.getDate("postdate"));
				reviewstar.setPostdate_hms(rs.getTime("postdate"));
				reviewstar.setIndex(rs.getString("index"));
				reviewstar.setTitle(rs.getString("title"));
				reviewList.add(reviewstar);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reviewList;
		
		
	}

	public int ReviewInsert(String m_id,String p_id,String content,String star,String title) {
		int Result = 0;

		String query = "insert into star_review( "
				+ " m_id,p_id,star,content,title) "
				+ " values( "
				+ " ?,?,?,?,?)";

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, m_id);
			psmt.setString(2, p_id);
			psmt.setString(3, star);
			psmt.setString(4, content);
			psmt.setString(5, title);
		
			Result = psmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("������� ������Ʈ �� �����߻�");
			e.printStackTrace();
		}
		
		System.out.println("������� ������Ʈ ����!");
		return Result;

	}

	public ReviewStarDTO SelectReviewOne(int index) { 
		ReviewStarDTO review  = new ReviewStarDTO();	
		String sql = "select *from star_review";
		sql += " WHERE `index` = ? ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, index);
			rs = psmt.executeQuery();
			if(rs.next()) {
				review.setContent(rs.getString("content"));
				review.setIndex(rs.getString("index"));
				review.setM_id(rs.getString("m_id"));
				review.setP_id(rs.getString("p_id"));
				review.setStar(rs.getString("star"));
				review.setTitle(rs.getString("title"));
				review.setPostdate_ymd(rs.getDate("postdate"));
				review.setPostdate_hms(rs.getTime("postdate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return review;		
	}
	
}	
