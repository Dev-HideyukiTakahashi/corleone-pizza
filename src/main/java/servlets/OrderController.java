package servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.ClientDAO;
import model.dao.OrderDAO;
import model.dao.ProductDAO;
import model.entities.Client;
import model.entities.Order;
import model.entities.Product;

/**
 * /order
 */
public class OrderController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ProductDAO productDao = new ProductDAO();
	private ClientDAO clientDao   = new ClientDAO();
	private OrderDAO orderDAO	  = new OrderDAO();
	
	private static Order order = new Order();
	private static Client client = new Client();
	

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

			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("checkout")) 
			{

				if(prodCode != null) {
					Product product = productDao.productByCode(prodCode);
					order.addProduct(product);
				}
				
				if(delete != null && !delete.isEmpty() && delete.equalsIgnoreCase("delete"))
				{
					Product product = productDao.productByCode(delCode);
					order.removeProduct(product);
				}
				
				if(clientId != null && !clientId.isEmpty()) {
					client = clientDao.clientById(Long.parseLong(clientId));
				}
				
				request.setAttribute("client", client);
				request.setAttribute("products", order.getProducts());

				RequestDispatcher redirect = request.getRequestDispatcher("/pages/orders/checkout.jsp");
				redirect.forward(request, response);
			}
			
			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("final")) 
			{
				String comments = request.getParameter("comments");
				
				order.setDate(LocalDateTime.now());
				
				orderDAO.insert(comments, client, order);
				
				order.getProducts().clear();
				client = new Client();
			}
			
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("listAll"))
			{
				List<Order> listAll =  orderDAO.findAll();
				
				Collections.sort(listAll);
				
				request.setAttribute("orderData", listAll);
				RequestDispatcher redirect = request.getRequestDispatcher("/pages/orders/orders.jsp");
				redirect.forward(request, response);
			}
			
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("finalView"))
			{

				String codeString = request.getParameter("code");
				
				Order order = orderDAO.findByCode(Long.parseLong(codeString));
				
				ObjectMapper mapper = new ObjectMapper();
				String JSON = mapper.writeValueAsString(order);
				
				// Forcando a formatacao do JSON para UTF-8
				response.setCharacterEncoding("UTF-8");
				// Enviando o JSON no response do AJAX
				response.getWriter().write(JSON);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}
	}

}
