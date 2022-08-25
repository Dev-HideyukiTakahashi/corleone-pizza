package servlets;

import java.io.IOException;
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
 * Mapeado em sistema: /products Servlet para manipular pizza e bebidas com
 * banco de dados O Filter está responsavel pelo rollback
 */
public class ServletProduct extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private ProductDAO productDAO = new ProductDAO();
	
	public ServletProduct() {
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
			if (type != null && !type.isEmpty() && type.equalsIgnoreCase("pizzaCode")) 
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
			if (prodType != null && !prodType.isEmpty() && prodType.equalsIgnoreCase("Pizza")) 
			{
				List<Product> items = new ArrayList<>();
				
				items = productDAO.productSearch(prodType);
				
				ObjectMapper mapper = new ObjectMapper();
				String JSON = mapper.writeValueAsString(items);
				
				response.getWriter().write(JSON);
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
			String description = request.getParameter("description");
			String updateData  = request.getParameter("updateData");
			
			if(updateData != null && !updateData.isEmpty() && updateData.equalsIgnoreCase("updateData")) 
			{
				Product item = new Product();
				productDAO.productUpdate(code, description);
				response.getWriter().write("atualizado");
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
