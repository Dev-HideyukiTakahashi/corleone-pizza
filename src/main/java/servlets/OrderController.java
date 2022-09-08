package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ClientDAO;
import model.dao.ProductDAO;
import model.entities.Client;
import model.entities.Product;

/**
 * /order
 */
public class OrderController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ProductDAO productDao = new ProductDAO();
	private ClientDAO clientDao   = new ClientDAO();
	
	private static List<Product> products = new ArrayList<>();
	private static Client client;
	

	public OrderController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action       = request.getParameter("action");
			String prodCode     = request.getParameter("prodCode");
			String delCode     = request.getParameter("delCode");
			String clientId	    = request.getParameter("clientId");
			String delete		= request.getParameter("delete");

			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("checkout")) {

				if(prodCode != null) {
					Product product = productDao.productByCode(prodCode);
					products.add(product);
				}
				
				if(delete != null && !delete.isEmpty() && delete.equalsIgnoreCase("delete"))
				{
					Product product = productDao.productByCode(delCode);
					products.remove(product);
				}
				
				if(clientId != null && !clientId.isEmpty()) {
					client = clientDao.clientById(Long.parseLong(clientId));
				}
				
				request.setAttribute("client", client);
				request.setAttribute("products", products);

				RequestDispatcher redirect = request.getRequestDispatcher("/pages/orders/checkout.jsp");
				redirect.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
