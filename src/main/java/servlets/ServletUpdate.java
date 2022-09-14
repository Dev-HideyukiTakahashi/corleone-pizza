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
 * Mapeado em sistema: /update 
 * Servlet para atualizar um novo cliente
 * O Filter esta responsavel pelo rollback
 */
public class ServletUpdate extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ClientDAO clientDAO = new ClientDAO();
	
	// Classe utilitaria para guardar o id de qual usuario esta logado em sistema
	private ServletUtil connectedId = new ServletUtil();
	
	public ServletUpdate() {

		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try
		{
			// Algoritmo da busca de cliente por telefone
			String field  = request.getParameter("field");
			List<Client> client = new ArrayList<>();
			
			if(field != null && !field.isEmpty()) 
			{
				client = clientDAO.clientSearch("empty", field, connectedId.getUserConnected(request));
				
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
				RequestDispatcher redirecionador = request.getRequestDispatcher("pages/clients/delete.jsp");
				redirecionador.forward(request, response);
			}
			
			// Algoritmo Delete
			String valueDelete = request.getParameter("phone");
			if(valueDelete != null) {
				clientDAO.clientDelete(valueDelete);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		try
		{
			Client client = new Client();
			
			String name 	 = request.getParameter("name");
			String phone 	 = request.getParameter("phone");
			String email 	 = request.getParameter("email"); // Recuperando dados do form em register.jsp
			String adress	 = request.getParameter("adress");
			String reference = request.getParameter("reference");

			Client newClient = new Client(name, phone, email, adress, reference);
			
			clientDAO.clientUpdate(newClient);
			
			response.getWriter().write("atualizado");
		}
		catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}
	}
}
