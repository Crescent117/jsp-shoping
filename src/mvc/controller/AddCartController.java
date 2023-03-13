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

@WebServlet("/addCart.do")
public class AddCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/addCart.do/doGet()");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/addCart.do/doPost()");
		String id = request.getParameter("id");
		String color =request.getParameter("color");
		String size =request.getParameter("size");
		System.out.println(id);
		System.out.println(color);
		System.out.println(size);
		
		if (id == null || id.trim().equals("")) {
			response.sendRedirect("products.jsp");
			return;
		}
		
		//ProductRepository dao = ProductRepository.getInstance();
		ProductRepository dao = new ProductRepository(id,color,size);
		//System.out.println(id);
		ProductDTO product = dao.getProductById(id);
		System.out.println(product);
		if (product == null) {
			response.sendRedirect("exceptionNoProductId.jsp");
		}

		ArrayList<ProductDTO> goodsList = dao.getAllProducts();
		ProductDTO goods = new ProductDTO();
		for (int i = 0; i < goodsList.size(); i++) {
			goods = goodsList.get(i);
			if (goods.getP_id().equals(id)) { 			
				break;
			}
		}
		
		HttpSession session = request.getSession();
		ArrayList<ProductDTO> list = (ArrayList<ProductDTO>) session.getAttribute("cartlist");
		if (list == null) { 
			list = new ArrayList<ProductDTO>();
			session.setAttribute("cartlist", list);
		}

		int cnt = 0;
		ProductDTO goodsQnt = new ProductDTO();
		for (int i = 0; i < list.size(); i++) {
			goodsQnt = list.get(i);
			if (goodsQnt.getP_id().equals(id)) {
				cnt++;
				int orderQuantity = Integer.parseInt(goodsQnt.getP_quantity()) + 1;
				goodsQnt.setP_quantity(orderQuantity+"");
			}
		}

		if (cnt == 0) { 
			goods.setP_quantity(1+"");
			list.add(goods);
		}
		
		
		response.sendRedirect("/WebMarket/Bcontroller.do?id=" + id);
		//request.getRequestDispatcher("/product.jsp?id=" + id).forward(request, response);

	}





}
