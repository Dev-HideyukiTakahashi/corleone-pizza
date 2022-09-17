package servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	
	public OrderController() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action       = request.getParameter("action");
			String prodCode     = request.getParameter("prodCode");
			String delCode      = request.getParameter("delCode");
			String clientId	    = request.getParameter("clientId");
			String delete		= request.getParameter("delete");

			HttpSession session = request.getSession();

			// Tela de '+pedido' checkout do pedido
			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("checkout")) 
			{
				// se nao tem produto ainda no checkout
				if(session.getAttribute("products") == null) 
				{
					// a lista aponta para a sessao vazia e instancia uma arraylist
					List<Product> products = (List<Product>) session.getAttribute("products");
					products = new ArrayList<>();
					
					// Adiciona o produto pelo ID, a chamada e feita direto da tela do produto no icon da pizza
					if(prodCode != null) 
					{
						products.add(productDao.productByCode(prodCode));
						session.setAttribute("products", products);
					}
				}
				else
				{
					List<Product> products = (List<Product>) session.getAttribute("products");
					
					if(prodCode != null) 
					{
						products.add(productDao.productByCode(prodCode));
						session.setAttribute("products", products);
					}
				}

				
				// Removendo um produto no checkout
				if(delete != null && !delete.isEmpty() && delete.equalsIgnoreCase("delete"))
				{
					Product product = productDao.productByCode(delCode);
					// recuperando a lista da sessao para atualizar com o delete
					List<Product> products = (List<Product>) session.getAttribute("products");
					products.remove(product);
					session.setAttribute("products", products);
				}
				
				// Se o campo de cliente for preenchido recuperar os dados
				if(clientId != null && !clientId.isEmpty()) {
					Client client = clientDao.clientById(Long.parseLong(clientId));
					session.setAttribute("client", client);
				}
					
				request.setAttribute("client", (Client)session.getAttribute("client"));
				request.setAttribute("products", (List<Product>)session.getAttribute("products"));

				RequestDispatcher redirect = request.getRequestDispatcher("/pages/orders/checkout.jsp");
				redirect.forward(request, response);
			}
			
			// Clicando em finalizar pedido
			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("final")) 
			{
				String comments = request.getParameter("comments");
				
				Order order = new Order();
				order.setDate(LocalDateTime.now());
				order.getProducts().addAll((List<Product>)session.getAttribute("products"));
				order.setOrderClient((Client) session.getAttribute("client"));
				
				orderDAO.insert(comments, order.getOrderClient(), order);
				
				session.setAttribute("products", null);
				session.setAttribute("client", null);
				
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
