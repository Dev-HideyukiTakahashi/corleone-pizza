package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.ProductDAO;
import model.dao.UserDAO;
import model.entities.Product;
import model.entities.User;


/**
 * The Class DrinkController. mapped /drink
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */

public class DrinkController extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Classe utilitaria para recuperar o id do usuario da sessao. */
	private ServletUtil connectedId = new ServletUtil();

	/** The product DAO. */
	private ProductDAO productDAO = new ProductDAO();
	
	/** The user DAO. */
	private UserDAO   userDAO	  = new UserDAO();
	
	/**
	 * Instantiates a new drink controller.
	 */
	public DrinkController() {
		super();
	}

	/**
	 * "Search" busca os dados para preencher o modal da view de editar.
	 * Lista de forma dinamica todos os produtos da categoria na view, ao abrir o menu do "sidebar".
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Boolean isAdmin = null;
		try
		{
			String prodType    = request.getParameter("prodType");
			String type		   = request.getParameter("type");
			String code        = request.getParameter("code");
			
			if (type != null && !type.isEmpty() && type.equalsIgnoreCase("search")) 
			{
				Product 	 item 	= productDAO.productByCode(code);
				ObjectMapper mapper = new ObjectMapper();
				String		 JSON   = mapper.writeValueAsString(item);
				
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSON);
			}			

			if (prodType != null && !prodType.isEmpty() && prodType.equalsIgnoreCase("drink")) 
			{
				isAdmin = connectedId.getUserConnected(request) == 1L ? true : false;
				String 	       page = request.getParameter("page");
				page 		        = page == null ? "0" : page;
				List<Product> items = productDAO.productSearchPage(prodType, Integer.parseInt(page));
				
				request.setAttribute("numberPage", Integer.parseInt(page));
				request.setAttribute("drinkData", items);
				request.setAttribute("totalPages", productDAO.totalPages(prodType));
				request.setAttribute("isAdmin", isAdmin);
				request.getRequestDispatcher("pages/products/drinks.jsp").forward(request, response);
			}
		}
		catch(Exception e) {
			if(isAdmin == null) {
				request.getRequestDispatcher("/endsession.jsp").forward(request, response);
			}
			else {
				e.printStackTrace();
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}
	}

	/**
	 * Atualiza os dados do campo selecionado.
	 * Apenas Admin altera preco e descricao.
	 * Apenas Admin pode registrar um novo produto. 
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try 
		{
			String code        = request.getParameter("code");
			String value	   = request.getParameter("description");
			String updateData  = request.getParameter("updateData");
			
			if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updateDescription")){
				productDAO.productUpdate(code, value, "updateDescription", null);
			}
			else if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updatePrice")){
				productDAO.productUpdate(code, value, "updatePrice", null);
			}
			else if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updateName")){
				User user =  userDAO.findUserById(connectedId.getUserConnected(request));
				productDAO.productUpdate(code, value, "updateName", user);
			}
			
			String action  			  = request.getParameter("action");
			String newName    	      = request.getParameter("newName");
			String newDescription     = request.getParameter("newDescription");
			String newPrice           = request.getParameter("newPrice");

			
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("insert")) {
				Double price    = Double.parseDouble(newPrice.replace(",", ".").replace("-", "."));
				Product product = new Product(newName, newDescription, price, "Drink");
				
				productDAO.productInsert(product);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
