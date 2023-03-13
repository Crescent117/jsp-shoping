package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductRepository;
import dto.ProductDTO;

@WebServlet("/removeCart.do")
public class RemoveCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/removeCart.do/doGet()");
		
		String id = request.getParameter("id");
		if (id == null || id.trim().equals("")) {
			response.sendRedirect("products.jsp");
			return;
		}

		ProductRepository dao = ProductRepository.getInstance();
		
		ProductDTO product = dao.getProductById(id);
		if (product == null) {
			response.sendRedirect("exceptionNoProductId.jsp");
		}
		
		HttpSession session = request.getSession();
		ArrayList<ProductDTO> cartList = (ArrayList<ProductDTO>) session.getAttribute("cartlist");
		ProductDTO goodsQnt = new ProductDTO();
		for (int i = 0; i < cartList.size(); i++) { // 상품리스트 하나씩 출력하기
			goodsQnt = cartList.get(i);
			if (goodsQnt.getP_id().equals(id)) {
				cartList.remove(goodsQnt);
			}
		}

		response.sendRedirect("cart.jsp");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/removeCart.do/doPost()");
	}

}
