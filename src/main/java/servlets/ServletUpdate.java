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
 * The Class ServletUpdate. mapped /update
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
public class ServletUpdate extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The client DAO. */
	private ClientDAO clientDAO = new ClientDAO();

	/** Classe utilitaria para recuperar o id do usuario da sessao. */
	private ServletUtil connectedId = new ServletUtil();
	
	/**
	 * Instantiates a new servlet update.
	 */
	public ServletUpdate() {

		super();
	}

	/**
	 * Recupera uma lista com todos clientes cadastrados pelo usuario da sessao.
	 * A busca eh feita pelo telefone.
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
			String field  = request.getParameter("field");
			List<Client> client = new ArrayList<>();
			
			if(field != null && !field.isEmpty()) 
			{
				client = clientDAO.clientSearch("empty", field, connectedId.getUserConnected(request));
				
				ObjectMapper mapper = new ObjectMapper();
				String JSON = mapper.writeValueAsString(client);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSON);
			}
			else{
				request.getRequestDispatcher("pages/clients/delete.jsp").forward(request, response);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Atualiza os dados do cliente.
	 * O telefone nao eh valido se ja for cadastrado em sistema.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		try
		{
			String id 		     = request.getParameter("id");
			String name 		 = request.getParameter("name");
			String newPhone 	 = request.getParameter("newPhone");
			String phone 		 = request.getParameter("phone");
			String email 		 = request.getParameter("email"); 
			String adress		 = request.getParameter("adress");
			String reference	 = request.getParameter("reference");
			Client newClient 	 = new Client(name, newPhone, email, adress, reference);
			
			newClient.setId(Long.parseLong(id));
			
			if(newPhone.equals(phone)) {
				clientDAO.clientUpdate(newClient);
				response.getWriter().write("success");
			}
			else if (!newPhone.equals(phone) && !clientDAO.clientExists(newClient)){
				clientDAO.clientUpdate(newClient);
				response.getWriter().write("success");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
