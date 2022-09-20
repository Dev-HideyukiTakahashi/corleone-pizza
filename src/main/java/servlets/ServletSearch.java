package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.ClientDAO;
import model.entities.Client;

/**
 * The Class ServletSearch. mapped /search
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */

public class ServletSearch extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The client DAO. */
	private ClientDAO clientDAO = new ClientDAO();
	
	/** Classe utilitaria para recuperar o id do usuario da sessao. */
	private ServletUtil connectedId = new ServletUtil();
	
	/**
	 * Instantiates a new servlet search.
	 */
	public ServletSearch() {
		super();
	}

	/**
	 * Busca uma lista de clientes registrados pelo usuario da sessao.
	 * Busca uma lista de clientes por nome ou telefone.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try
		{
			String action = request.getParameter("action");
			String field  = request.getParameter("field");
			String select = request.getParameter("select");

			List<Client> client = new ArrayList<>();
			ObjectMapper mapper = new ObjectMapper();
			
			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("searchAll")) 
			{
				client = clientDAO.clientSearchAll(connectedId.getUserConnected(request));
				
				request.setAttribute("clientData", client); 
				request.setAttribute("clientDataSize", client.size()); 
				request.getRequestDispatcher("pages/clients/list.jsp").forward(request, response);
			}
			
			if(action == null) {
				if(select.equals("nameOption") && field != null && !field.isEmpty()) 
				{
					client = clientDAO.clientSearch(select, field, connectedId.getUserConnected(request));

					String JSON = mapper.writeValueAsString(client);
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(JSON);
				}
				else if(select.equals("phoneOption") && field != null && !field.isEmpty())
				{
					client = clientDAO.clientSearch(select, field, connectedId.getUserConnected(request));
					String JSON = mapper.writeValueAsString(client);
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(JSON);
				}
				else{
					request.getRequestDispatcher("pages/clients/find.jsp").forward(request, response);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
