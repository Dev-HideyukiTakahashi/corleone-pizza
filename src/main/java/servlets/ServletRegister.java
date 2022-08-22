package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ClientDAO;
import model.entities.Client;

import java.io.IOException;
import java.sql.SQLException;

/**
 *  Mapeado em sistema: /register
 *  Servlet para cadastrar um novo cliente 
 */
public class ServletRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletRegister() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try 
		{
			ClientDAO clientDAO = new ClientDAO();
			
			String name		 = request.getParameter("name");
			String phone	 = request.getParameter("phone");
			String email 	 = request.getParameter("email"); // Recuperando dados do form em register.jsp
			String adress    = request.getParameter("adress");
			String reference = request.getParameter("reference");
			
			Client newClient = new Client(name, phone, email, adress, reference);

			// Registrando um novo usu�rio
			if(clientDAO.clientExists(newClient)) {
				request.setAttribute("registerFail", "J� existe cliente cadastrado com esse telefone.");
				request.setAttribute("clientData", newClient);
			}
			else {
				clientDAO.insertClient(newClient);
				request.setAttribute("registerSucess", "Cliente registrado com sucesso!");
			}
			
			RequestDispatcher redirecionador = request.getRequestDispatcher("pages/clients/register.jsp");
			redirecionador.forward(request, response);
		}
		catch(SQLException e) {
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}

	}

}
