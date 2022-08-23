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
import model.dao.ClientDAO;
import model.entities.Client;

/**
 *  Mapeado em sistema: /search 
 *  Servlet para buscar dados no banco de dados
 */
public class ServletSearch extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private ClientDAO clientDAO = new ClientDAO();
	
	public ServletSearch() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String field  = request.getParameter("field");
		String select = request.getParameter("select");
		List<Client> client = new ArrayList<>();
		
		if(select.equals("nameOption") && field != null && !field.isEmpty()) {
			client = clientDAO.clientSearch(select, field);
			
			// Biblioteca Jackson Databind adicionada no POM, trabalhando com JSON
			ObjectMapper mapper = new ObjectMapper();
			String JSON = mapper.writeValueAsString(client);
			// Enviando o JSON no response do AJAX
			response.getWriter().write(JSON);
		}
		else if(select.equals("phoneOption") && field != null && !field.isEmpty()){
			client = clientDAO.clientSearch(select, field);

			// Biblioteca Jackson Databind adicionada no POM, trabalhando com JSON
			ObjectMapper mapper = new ObjectMapper();
			String JSON = mapper.writeValueAsString(client);
			// Enviando o JSON no response do AJAX
			response.getWriter().write(JSON);
		}
		else {
			RequestDispatcher redirecionador = request.getRequestDispatcher("pages/client/find.jsp");
			redirecionador.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
