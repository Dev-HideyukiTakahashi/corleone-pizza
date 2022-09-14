package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.ClientDAO;
import model.entities.Client;

/**
 * Mapeado em sistema: /register 
 * Servlet para cadastrar um novo cliente
 * O Filter esta responsavel pelo rollback
 */
public class ServletRegister extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ClientDAO clientDAO = new ClientDAO();
	
	// Classe utilitaria para guardar o id de qual usuario esta logado em sistema
	private ServletUtil connectedId = new ServletUtil();

	public ServletRegister() {
		super();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try 
		{

			String name 	 = request.getParameter("name");
			String phone 	 = request.getParameter("phone");
			String email 	 = request.getParameter("email"); // Recuperando dados do form em register.jsp
			String adress	 = request.getParameter("adress");
			String reference = request.getParameter("reference");

			Client newClient = new Client(name, phone, email, adress, reference);

			// Registrando um novo usuario
			// Verificando se ja existe um cliente com esse telefone
			if (clientDAO.clientExists(newClient)) {
				request.setAttribute("clientData", newClient);
			}
			// Se nao existe cliente com esse telefone, valida para novo cadastro
			else 
			{
				clientDAO.insertClient(newClient, connectedId.getUserConnected(request));
				response.getWriter().write("registrado");
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}
	}
}
