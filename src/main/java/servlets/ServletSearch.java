package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.ClientDAO;
import model.entities.Client;

/**
 *  Mapeado em sistema: /search 
 *  Servlet para buscar dados no banco de dados
 *  O Filter esta responsavel pelo rollback
 */
public class ServletSearch extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private ClientDAO clientDAO = new ClientDAO();
	
	// Classe utilitaria para guardar o id de qual usuario esta logado em sistema
	private ServletUtil connectedId = new ServletUtil();
	
	public ServletSearch() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try
		{
			String action = request.getParameter("action");
			String field  = request.getParameter("field");
			String select = request.getParameter("select");
			List<Client> client = new ArrayList<>();
			
			// Enviando requisicao com lista de todos os clientes
			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("searchAll")) 
			{
				
				client = clientDAO.clientSearchAll(connectedId.getUserConnected(request));
				
				request.setAttribute("clientData", client); 
				request.setAttribute("clientDataSize", client.size()); 
				RequestDispatcher redireciona = request.getRequestDispatcher("pages/clients/list.jsp");

				redireciona.forward(request, response);				
			}
			
			// Enviando requisicao com lista de todos os clientes filtrados por telefone ou nome
			if(action == null) {
				if(select.equals("nameOption") && field != null && !field.isEmpty()) 
				{
					client = clientDAO.clientSearch(select, field, connectedId.getUserConnected(request));
					// Biblioteca Jackson Databind adicionada no POM, trabalhando com JSON
					ObjectMapper mapper = new ObjectMapper();
					String JSON = mapper.writeValueAsString(client);
					// Forcando a formatacao do JSON para UTF-8
					response.setCharacterEncoding("UTF-8");
					// Enviando o JSON no response do AJAX
					response.getWriter().write(JSON);
				}
				else if(select.equals("phoneOption") && field != null && !field.isEmpty())
				{
					client = clientDAO.clientSearch(select, field, connectedId.getUserConnected(request));

					// Biblioteca Jackson Databind adicionada no POM, trabalhando com JSON
					ObjectMapper mapper = new ObjectMapper();
					String JSON = mapper.writeValueAsString(client);
					// Forcando a formatacao do JSON para UTF-8
					response.setCharacterEncoding("UTF-8");
					// Enviando o JSON no response do AJAX
					response.getWriter().write(JSON);
				}
				else 
				{
					RequestDispatcher redirecionador = request.getRequestDispatcher("pages/clients/find.jsp");
					redirecionador.forward(request, response);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}
	}
}
