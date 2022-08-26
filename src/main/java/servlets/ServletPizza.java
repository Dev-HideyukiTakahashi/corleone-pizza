package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;
import model.entities.Product;

/**
 * Mapeado em sistema: /pizzas 
 * Servlet para manipular os dados das pizzas
 */
public class ServletPizza extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private ProductDAO productDAO = new ProductDAO();
	
	public ServletPizza() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try
		{
			String prodType    = request.getParameter("prodType");
			String type		   = request.getParameter("type");
			String code        = request.getParameter("code");
			
			// Enviando requisição com todos os dados do produto pelo código
			if (type != null && !type.isEmpty() && type.equalsIgnoreCase("search")) 
			{
				Product item = new Product();
				item = productDAO.productByCode(code);
				// Biblioteca Jackson Databind adicionada no POM, trabalhando com JSON
				ObjectMapper mapper = new ObjectMapper();
				String JSON = mapper.writeValueAsString(item);
				// Enviando o JSON no response do AJAX
				response.getWriter().write(JSON);
			}			

			// Enviando requisição com lista de todos os produtos de acordo com filtro
			// Responsável por carregar dinamicamente a pagina 'sabores'
			if (prodType != null && !prodType.isEmpty() && prodType.equalsIgnoreCase("pizza")) 
			{
				List<Product> items = new ArrayList<>();
				
				items = productDAO.productSearch(prodType);
				
				request.setAttribute("pizzaData", items);	
				RequestDispatcher redireciona = request.getRequestDispatcher("pages/products/pizzas.jsp");
				redireciona.forward(request, response);		
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try 
		{
			String code        = request.getParameter("code");
			String value	   = request.getParameter("description");
			String updateData  = request.getParameter("updateData");
			
			if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updatePizza")) 
			{
				productDAO.productUpdate(code, value, "updatePizza");
			}
			
			if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updatePrice")) 
			{
				productDAO.productUpdate(code, value, "updatePrice");
			}
			
			if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updateName")) 
			{
				productDAO.productUpdate(code, value, "updateName");
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
