package servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	private static Order order      = new Order();
	private static Client client    = new Client();
	private ServletUtil connectedId = new ServletUtil();
	
	private Map<Long, Order> map = new HashMap<>();
	

	public OrderController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action       = request.getParameter("action");
			String prodCode     = request.getParameter("prodCode");
			String delCode      = request.getParameter("delCode");
			String clientId	    = request.getParameter("clientId");
			String delete		= request.getParameter("delete");

			// Tela de '+pedido' checkout do pedido
			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("checkout")) 
			{

				// Adiciona o produto pelo ID, a chamada e feita direto da tela do produto no icon da pizza
				if(prodCode != null) {
					Product product = productDao.productByCode(prodCode);
					order.addProduct(product);
					map.put(connectedId.getUserConnected(request), order);
				}
				
				// Removendo um produto no checkout
				if(delete != null && !delete.isEmpty() && delete.equalsIgnoreCase("delete"))
				{
					Product product = productDao.productByCode(delCode);
					order.removeProduct(product);
				}
				
				// Se o campo de cliente for preenchido recuperar os dados
				if(clientId != null && !clientId.isEmpty()) {
					client = clientDao.clientById(Long.parseLong(clientId));
				}
				
				// HashMap nao contem o ID do user logado, significa que eh outro usuario em outra sessao
				// portanto limpa toda a tela de checkout
				if(!map.containsKey(connectedId.getUserConnected(request))) {
					map.clear();
					client = new Client();
					order  = new Order();
					request.setAttribute("products", null);
				}
				else {
					
					request.setAttribute("client", client);
					request.setAttribute("products", map.get(connectedId.getUserConnected(request)).getProducts());
				}

				RequestDispatcher redirect = request.getRequestDispatcher("/pages/orders/checkout.jsp");
				redirect.forward(request, response);
			}
			
			// Clicando em finalizar pedido
			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("final")) 
			{
				String comments = request.getParameter("comments");
				
				order.setDate(LocalDateTime.now());
				
				orderDAO.insert(comments, client, order);
				
				map.clear();
				client = new Client();
				order  = new Order();
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
