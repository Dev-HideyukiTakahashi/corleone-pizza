package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ClientDAO;
import model.entities.Client;

/**
 * The Class ServletRegister. mapped /register
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
public class ServletRegister extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The client DAO. */
	private ClientDAO clientDAO = new ClientDAO();
	
	/** Classe utilitaria para recuperar o id do usuario da sessao. */
	private ServletUtil connectedId = new ServletUtil();

	/**
	 * Instantiates a new servlet register.
	 */
	public ServletRegister() {
		super();
	}

	/**
	 * Cadastra um novo cliente no banco de dados.
	 * A condicao eh nao ter um cliente no banco de dados com o telefone desejado.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try 
		{
			String name 	 = request.getParameter("name");
			String phone 	 = request.getParameter("phone");
			String email 	 = request.getParameter("email"); 
			String adress	 = request.getParameter("adress");
			String reference = request.getParameter("reference");

			Client newClient = new Client(name, phone, email, adress, reference);

			if (!clientDAO.clientExists(newClient)) {
				clientDAO.insertClient(newClient, connectedId.getUserConnected(request));
				response.getWriter().write("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
