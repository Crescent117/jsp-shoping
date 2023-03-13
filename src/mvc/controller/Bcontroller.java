package mvc.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MVCBoardDAO;
import dto.ProductDTO;
import dto.ReviewStarDTO;
import mvc.model.BoardDAO;
import mvc.model.BoardDTO;

@WebServlet("*.po")
public class Bcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet()");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		doPost(request, response);
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		String m_id=request.getParameter("sessionId");
		String p_id = request.getParameter("p_id");
		String pageTemp = request.getParameter("pageNum");
		//System.out.println("너 실행되냐?"); 된다



		if (command.equals("/Bcontroller.po")) {//��ϵ� �� ��� ������ ����ϱ�
			requestPaging(request);
			request.getRequestDispatcher("/product.jsp?p_id="+p_id).forward(request, response);
			//response.sendRedirect("/WebMarket/product.jsp?p_id="+p_id);
		} else if(command.equals("/ReviewDetailController.po")) {
			ReviewDetailController(request);
			request.getRequestDispatcher("/ProductReview/productreview.jsp?p_id="+p_id).forward(request, response);
		}

	}

	
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("dopost()");
		//System.out.println("너 실행되냐 post");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		String p_id = request.getParameter("p_id");
		
		String pageTemp = request.getParameter("pageNum");
		if (command.equals("/ReviewController.po")) {//��ϵ� �� ��� ������ ����ϱ�
			//System.out.println("너 되니?");
			response.getWriter().write(getreview(p_id,pageTemp));
			//response.sendRedirect("/WebMarket/product.jsp?p_id="+p_id);
		}
		
		
		
	}

	
	public String getreview(String p_id,String pageTemp) {
		//System.out.println("실행됐냐2");
		MVCBoardDAO dao = new MVCBoardDAO();
		Map<String, Object> map = new HashMap<String, Object>();
		//페이징
		int totalCount = Integer.parseInt(dao.SelectCountReview(p_id));
		int pageSize =5;
		int blockPage = 5;
		int totalPage = (int)Math.ceil((double)totalCount / pageSize); // 전체 페이지 수
		int pageNum = 1; // 바꿔가면서 테스트 1~10 =>1, 11~20 => 11
		if (pageTemp != null && !pageTemp.equals(""))
			pageNum = Integer.parseInt(pageTemp);
		int start = (pageNum - 1) * pageSize + 1;  // 첫 게시물 번호
		int end = pageNum * pageSize; // 마지막 게시물 번호
		map.put("start", start);
		map.put("end", end);
		
		//출력
		if(p_id == null) p_id="";
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		List<ReviewStarDTO> reviewList = dao.SelectStarReView(p_id,map);
		
		dao.close();
		//System.out.println(reviewList);
		for(int i = 0; i<reviewList.size(); i++) {
			result.append("[{\"m_id\":\""+  reviewList.get(i).getM_id()+"\"},");
			result.append("{\"star\":\""+  reviewList.get(i).getStar()+"\"},");
			result.append("{\"content\":\""+  reviewList.get(i).getContent()+"\"},");
			result.append("{\"time\":\""+  reviewList.get(i).getPostdate_ymd() +" "+  reviewList.get(i).getPostdate_hms()+"\"},");
			result.append("{\"index\":\""+  reviewList.get(i).getIndex()+"\"},");
			result.append("{\"title\":\""+  reviewList.get(i).getTitle()+"\"},");
			result.append("{\"p_id\":\""+  reviewList.get(i).getP_id()+"\"}],");
		}
		result.deleteCharAt(result.length()-1);
		result.append("]}");
		//System.out.println(result);
		
		return result.toString();
		
	}
	
	public void requestPaging(HttpServletRequest request) {
		String p_id = request.getParameter("p_id");
		//System.out.println(p_id);
		Map<String, Object> map = new HashMap<String, Object>();
		MVCBoardDAO mdao = new MVCBoardDAO();
		ProductDTO dto = mdao.pSelectView(p_id);
		
		List<ProductDTO> size = mdao.SelectSizeView(p_id);
		List<ProductDTO> color = mdao.SelectColorView(p_id);
		
		int totalCount = Integer.parseInt(mdao.SelectCountReview(p_id));
		int pageSize =5;
		int blockPage = 5;
		int totalPage = (int)Math.ceil((double)totalCount / pageSize); // 전체 페이지 수
		int pageNum = 1; // 바꿔가면서 테스트 1~10 =>1, 11~20 => 11
		String pageTemp = request.getParameter("pageNum");
		if (pageTemp != null && !pageTemp.equals(""))
			pageNum = Integer.parseInt(pageTemp);
		int start = (pageNum - 1) * pageSize + 1;  // 첫 게시물 번호
		int end = pageNum * pageSize; // 마지막 게시물 번호
		map.put("start", start);
		map.put("end", end);

		List<ReviewStarDTO> star_review_result= mdao.SelectStarReView(p_id,map);
		
		mdao.close();
		
		
		request.setAttribute("size", size);
		request.setAttribute("color", color);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("blockPage", blockPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("dto", dto);
		request.setAttribute("review", star_review_result);

	}

	public void ReviewDetailController(HttpServletRequest request) {
		int index = Integer.parseInt(request.getParameter("index"));
		MVCBoardDAO mdao = new MVCBoardDAO();
		ReviewStarDTO review  = mdao.SelectReviewOne(index);
		
		request.setAttribute("review", review);

	}
	
}
