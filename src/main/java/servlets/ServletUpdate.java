package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entities.Client;

/**
 * Mapeado em sistema: /update Servlet para atualizar/deletar um novo cliente
 */
public class ServletUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletUpdate() {

		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Client client = new Client();

		String action = request.getParameter("action");
		String nome = "teste update";
		client.setName(nome);

		request.setAttribute("clientUpdate", client);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Client client = new Client();

		String nome = "teste update";
		client.setName(nome);

		request.setAttribute("clientUpdate", client);

	}

}
