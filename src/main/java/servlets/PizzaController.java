package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.AdminDAO;
import model.dao.ProductDAO;
import model.entities.Admin;
import model.entities.Product;

/**
 * Mapeado em sistema: /pizzas 
 * Servlet para manipular os dados das pizzas
 */
public class PizzaController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// Classe utilit�ria para guardar o id de qual usu�rio est� logado em sistema
	private ServletUtil connectedId = new ServletUtil();

	private ProductDAO productDAO = new ProductDAO();
	private AdminDAO   adminDAO	  = new AdminDAO();
	
	public PizzaController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Boolean isAdmin = null;
		try
		{
			String prodType    = request.getParameter("prodType");
			String type		   = request.getParameter("type");
			String code        = request.getParameter("code");
			
			// Enviando requisi��o com todos os dados do produto pelo c�digo
			if (type != null && !type.isEmpty() && type.equalsIgnoreCase("search")) 
			{
				Product item = productDAO.productByCode(code);
				// Biblioteca Jackson Databind adicionada no POM, trabalhando com JSON
				ObjectMapper mapper = new ObjectMapper();
				String JSON = mapper.writeValueAsString(item);
				// Enviando o JSON no response do AJAX
				response.getWriter().write(JSON);
			}			

			// Enviando requisi��o com lista de todos os produtos de acordo com filtro
			// Respons�vel por carregar dinamicamente a pagina 'sabores'
			if (prodType != null && !prodType.isEmpty() && prodType.equalsIgnoreCase("pizza")) 
			{
				List<Product> items = productDAO.productSearch(prodType);
				
				Collections.sort(items);
				for(Product p : items) {
					System.out.println(p.getProdCode());
				}
				// Checando se o usu�rio logado � userAdmin(ID: 1)
				isAdmin = connectedId.getUserConnected(request) == 1L ? true : false;
				
				request.setAttribute("pizzaData", items);
				request.setAttribute("isAdmin", isAdmin);
				RequestDispatcher redireciona = request.getRequestDispatcher("pages/products/pizzas.jsp");
				redireciona.forward(request, response);		
			}
		}
		catch(Exception e) {
			if(isAdmin == null) {
				RequestDispatcher redirecionador = request.getRequestDispatcher("/endsession.jsp");
				redirecionador.forward(request, response);
			}
			else {
				e.printStackTrace();
				RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
				redirecionador.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try 
		{
			// Requisi��o update
			String code        = request.getParameter("code");
			String value	   = request.getParameter("description");
			String updateData  = request.getParameter("updateData");
			
			if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updatePizza")) 
			{
				productDAO.productUpdate(code, value, "updatePizza", null);
			}
			
			else if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updatePrice")) 
			{
				productDAO.productUpdate(code, value, "updatePrice", null);
			}
			
			// M�todo que pode ser acessado por admin ou usu�rio
			else if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updateName")) 
			{
				Admin user =  adminDAO.findUserId(connectedId.getUserConnected(request));
				productDAO.productUpdate(code, value, "updateName", user);
			}
			
			
			// Requisi��o insert
			String action  			  = request.getParameter("action");
			String newName    	      = request.getParameter("newName");
			String newDescription     = request.getParameter("newDescription");
			String newPrice           = request.getParameter("newPrice");

			
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("insert")) {
				Double price    = Double.parseDouble(newPrice.replace(",", ".").replace("-", "."));
				Product product = new Product(newName, newDescription, price, "Pizza");
				
				productDAO.productInsert(product);
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}
		
	}
	
	
	public void listView(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException 
	{

	}

}
