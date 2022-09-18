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

import model.dao.ProductDAO;
import model.dao.UserDAO;
import model.entities.Product;
import model.entities.User;

/**
 * Mapeado em sistema: /pizzas 
 * Servlet para manipular os dados das pizzas
 */
public class PizzaController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// Classe utilitaria para guardar o id de qual usuario esta logado em sistema
	private ServletUtil connectedId = new ServletUtil();

	private ProductDAO productDAO = new ProductDAO();
	private UserDAO   userDAO	  = new UserDAO();
	
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
			
			// Enviando requisicao com todos os dados do produto pelo codigo
			if (type != null && !type.isEmpty() && type.equalsIgnoreCase("search")) 
			{
				Product item = productDAO.productByCode(code);
				// Biblioteca Jackson Databind adicionada no POM, trabalhando com JSON
				ObjectMapper mapper = new ObjectMapper();
				String JSON = mapper.writeValueAsString(item);
				// Forcando a formatacao do JSON para UTF-8
				response.setCharacterEncoding("UTF-8");
				// Enviando o JSON no response do AJAX
				response.getWriter().write(JSON);
			}			

			// Responsavel por carregar dinamicamente a pagina com todos produtos
			if (prodType != null && !prodType.isEmpty() && prodType.equalsIgnoreCase("pizza")) 
			{
				List<Product> items = productDAO.productSearch(prodType);
				
				
				//Ordenando a lista por ordem de cod para aparecer na tela
				Collections.sort(items);
				
				// Checando se o usuario logado e userAdmin(ID: 1)
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
			// Requisicao update
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
			
			// Metodo para alterar o nome, com log do usuario que alterou
			else if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updateName")) 
			{
				User user =  userDAO.findUserById(connectedId.getUserConnected(request));
				productDAO.productUpdate(code, value, "updateName", user);
			}
			
			// Requisicao insert
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
}
