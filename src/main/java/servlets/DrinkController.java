package servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.AdminDAO;
import model.dao.ProductDAO;
import model.entities.Admin;
import model.entities.Product;

/**
 *  /drink
 */
public class DrinkController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Classe utilitária para guardar o id de qual usuário está logado em sistema
	private ServletUtil connectedId = new ServletUtil();

	private ProductDAO productDAO = new ProductDAO();
	private AdminDAO   adminDAO	  = new AdminDAO();
	
	public DrinkController() {
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
			
			// Enviando requisição com todos os dados do produto pelo código
			if (type != null && !type.isEmpty() && type.equalsIgnoreCase("search")) 
			{
				Product item = productDAO.productByCode(code);
				// Biblioteca Jackson Databind adicionada no POM, trabalhando com JSON
				ObjectMapper mapper = new ObjectMapper();
				String JSON = mapper.writeValueAsString(item);
				// Enviando o JSON no response do AJAX
				response.getWriter().write(JSON);
			}			

			// Enviando requisição com lista de todos os produtos de acordo com filtro
			// Responsável por carregar dinamicamente a pagina 'sabores'
			if (prodType != null && !prodType.isEmpty() && prodType.equalsIgnoreCase("drink")) 
			{
				List<Product> items = productDAO.productSearch(prodType);
				
				
				//Ordenando a lista por ordem de cod para aparecer na tela
				Collections.sort(items);
				
				// Checando se o usuário logado é userAdmin(ID: 1)
				isAdmin = connectedId.getUserConnected(request) == 1L ? true : false;
				
				request.setAttribute("drinkData", items);
				request.setAttribute("isAdmin", isAdmin);
				RequestDispatcher redireciona = request.getRequestDispatcher("pages/products/drinks.jsp");
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
			// Requisição update
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
			
			// Método que pode ser acessado por admin ou usuário
			else if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updateName")) 
			{
				Admin user =  adminDAO.findUserId(connectedId.getUserConnected(request));
				productDAO.productUpdate(code, value, "updateName", user);
			}
			
			
			// Requisição insert
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
		catch(Exception e) 
		{
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}
		
	}

}