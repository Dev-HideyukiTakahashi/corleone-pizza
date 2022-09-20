package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.ClientDAO;
import model.dao.MotoboyDAO;
import model.dao.OrderDAO;
import model.dao.ProductDAO;
import model.entities.Client;
import model.entities.Motoboy;
import model.entities.Order;
import model.entities.Product;

/**
 * The Class OrderController. mapped /order
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
public class OrderController extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The product dao. */
	private ProductDAO productDao = new ProductDAO();
	
	/** The client dao. */
	private ClientDAO clientDao   = new ClientDAO();
	
	/** The order DAO. */
	private OrderDAO orderDAO	  = new OrderDAO();
	
	/** The motoboy DAO. */
	private MotoboyDAO motoboyDAO = new MotoboyDAO();
	
	/**
	 * Instantiates a new order controller.
	 */
	public OrderController() {
		super();
	}

	/**
	 * Mostra e coleta informacoes na view do checkout.
	 * Finaliza o pedido.
	 * Lista todos pedidos na view "Pedidos".
	 * Lista todos dados do pedido de acordo com o codigo.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		try 
		{
			String action       = request.getParameter("action");

			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("checkout"))	{
				checkoutAction(request,response);
			}
			else if (action != null && !action.isEmpty() && action.equalsIgnoreCase("final")){
				finalAction(request,response);
			}			
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("listAll")){
				listAllAction(request, response);
			}			
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("finalView")){
				finalViewAction(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}


	/**
	 * Atualiza o checkout de forma dinamica.
	 * Adiciona os produtos e cliente a sessao, para nao guardar chache no sistema.
	 * Se a sessao nao tiver produto, instancia uma nova ArrayList.
	 * Deleta o produto pelo codigo, e atualiza a sessao.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws SQLException the SQL exception
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unchecked")
	private void checkoutAction(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException 
	{
		String prodCode     = request.getParameter("prodCode");
		String clientId	    = request.getParameter("clientId");
		String delCode      = request.getParameter("delCode");
		String delete		= request.getParameter("delete");
		
		HttpSession session = request.getSession();
		
		List<Motoboy> list  = motoboyDAO.motoboySearchAll();
		request.setAttribute("motoboyData", list);
		
		if(session.getAttribute("products") == null) 
		{
			List<Product> products = (List<Product>) session.getAttribute("products");
			products = new ArrayList<>();
			
			if(prodCode != null){
				products.add(productDao.productByCode(prodCode));
				session.setAttribute("products", products);
			}
		}
		else
		{
			List<Product> products = (List<Product>) session.getAttribute("products");
			
			if(prodCode != null){
				products.add(productDao.productByCode(prodCode));
				session.setAttribute("products", products);
			}
		}
		
		if(delete != null && !delete.isEmpty() && delete.equalsIgnoreCase("delete"))
		{
			Product product = productDao.productByCode(delCode);
			List<Product> products = (List<Product>) session.getAttribute("products");
			products.remove(product);
			session.setAttribute("products", products);
		}
		
		if(clientId != null && !clientId.isEmpty()) {
			Client client = clientDao.clientById(Long.parseLong(clientId));
			session.setAttribute("client", client);
		}
			
		request.setAttribute("client", (Client)session.getAttribute("client"));
		request.setAttribute("products", (List<Product>)session.getAttribute("products"));
		request.getRequestDispatcher("/pages/orders/checkout.jsp").forward(request, response);
	}
	
	/**
	 * Ao clicar no botao finalizar na view do checkout.
	 * Registra um novo pedido no banco de dados com todos seus relacionamentos.
	 * A data eh gerada pelo proprio posgresql, formato Timestamp.
	 * Limpa os dados da sessao, prepara para um novo pedido.
	 * 
	 *
	 * @param request the request
	 * @param response the response
	 * @throws SQLException the SQL exception
	 */
	@SuppressWarnings("unchecked")
	private void finalAction(HttpServletRequest request, HttpServletResponse response) throws SQLException 
	{
		
		HttpSession session = request.getSession();
		String comments     = request.getParameter("comments");
		String motoboyName  = request.getParameter("motoboyName");
		
		Order order = new Order();
		order.getProducts().addAll((List<Product>)session.getAttribute("products"));
		order.setOrderClient((Client) session.getAttribute("client"));
		
		Motoboy motoboy = motoboyDAO.findByName(motoboyName);
		
		orderDAO.insert(comments, order.getOrderClient(), order, motoboy.getMotoboyId());
		
		session.setAttribute("products", null);
		session.setAttribute("client", null);
	}
	
	/**
	 * Na view "Pedidos", lista todos os pedidos realizados.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws SQLException the SQL exception
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void listAllAction(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException 
	{
		List<Order> listAll =  orderDAO.findAll();
		
		Collections.sort(listAll);
		
		request.setAttribute("orderData", listAll);
		request.getRequestDispatcher("/pages/orders/orders.jsp").forward(request, response);
	}
	
	/**
	 * Na view "Pedidos", botao "ver".
	 * Todos detalhes do pedido realizado.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws NumberFormatException the number format exception
	 * @throws SQLException the SQL exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void finalViewAction(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException, IOException 
	{
		String codeString = request.getParameter("code");
		
		Order order = orderDAO.findByCode(Long.parseLong(codeString));
		
		ObjectMapper mapper = new ObjectMapper();
		String JSON = mapper.writeValueAsString(order);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(JSON);
	}
}
