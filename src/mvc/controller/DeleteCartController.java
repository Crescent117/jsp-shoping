package mvc.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/deleteCart.do")
public class DeleteCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/deleteCart.do/doGet()");
		
		String cartId = request.getParameter("cartId");
		
		if (cartId == null || cartId.trim().equals("")) {
			response.sendRedirect("cart.jsp");
			return;
		}
		
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("cart.jsp");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
