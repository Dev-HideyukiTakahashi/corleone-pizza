package servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.AdminDAO;
import model.entities.Admin;

/**
 * 	Mapeado em sistema: /login
 *  Servlet para controlar a tela de login
 *  O Filter est� responsavel pelo rollback
 */
@WebServlet(urlPatterns = {"/pages/login", "/ServletLogin"})
public class ServletLogin extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;
	
	private AdminDAO adminDAO = new AdminDAO();
	
    public ServletLogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action"); // Argumento vindo da 'navbar / Log out'
		
		if(action != null && !action.isEmpty() && action.equalsIgnoreCase("logout")) 
		{
			request.getSession().invalidate(); // Termina a sess�o do usu�rio
			RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
			redirect.forward(request, response);
		}
		else {
			doPost(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			// Requisi��o da p�gina newuser submitUser()
			String newName 	     = request.getParameter("newName");
			String newPhone 	 = request.getParameter("newPhone");
			String newEmail 	 = request.getParameter("newEmail"); // Recuperando dados do form em newuser.jsp
			String newLogin	     = request.getParameter("newLogin");
			String newPassword   = request.getParameter("newPassword");
			String newPartner    = request.getParameter("newPartner");
			
			String action		 = request.getParameter("action");
			
			if(action != null) 
			{
				if(action.equalsIgnoreCase("insert") || !action.isEmpty()) 
				{
					Admin newUser = new Admin(newName, newPhone, newEmail, newLogin, newPassword, newPartner, null);
					adminDAO.insertUser(newUser);
				}
			}
			
			// Request de par�metros da tela de login
			String login     = request.getParameter("login");
			String password  = request.getParameter("password");		
			String url       = request.getParameter("url");
			
			// Validando se os dados foram preenchidos antes de acessar o sistema
			if(login != null && !login.isEmpty() && password != null && !password.isEmpty()) 
			{
				// Comparando os dados preenchidos com o banco de dados
				if(adminDAO.validateLogin(login, password))
				{
					// Usuario e senha confirmados com BD, inicia a sess�o com os dados do usuario logado
					Admin adminLogin = adminDAO.adminData(login);
					request.getSession().setAttribute("adminName", adminLogin.getAdminName());
					request.getSession().setAttribute("adminLogin", adminLogin.getLogin());
					
					// Configurando qual usu�rio est� utilizando o sistema
					Boolean isAdmin = adminLogin.getId() == 1? true : false;
					request.getSession().setAttribute("isAdmin", isAdmin);
					
					if(isAdmin) {
						request.getSession().setAttribute("adminOffice", "Administrador");
						// Poderia ter um identificador no banco de dados para foto de perfil
						request.getSession().setAttribute("adminImg", "/assets/images/faces/face.jpg");
					}
					else {
						request.getSession().setAttribute("adminOffice", "Usu�rio");
						// Poderia ter um identificador no banco de dados para foto de perfil
						request.getSession().setAttribute("adminImg", "/assets/images/faces/face2.jpg");
					}
					
					// Se o usu�rio n�o tentou acessar nenhuma page antes da tela de login redirecionar a main
					if(url == null || url.equals("null"))
					{
						url = "pages/main.jsp";
					}

					// Se o usu�rio tentou acessar alguma p�gina sem logar, ap�s o login com sucesso o mesmo acessa a p�gina desejada
					RequestDispatcher redirect = request.getRequestDispatcher(url);
					redirect.forward(request, response);
				}
				else  // Ap�s busca em banco de dados, n�o encotra usu�rio ou senha / ou ambos
				{
					RequestDispatcher redirecionador = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Usuario ou senha inv�lido");
					redirecionador.forward(request, response);
				}
			}
			else     // N�o preencheu nenhum campo e clicou em login
			{
				RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("msg", "Todos campos precisam ser preenchidos");
				redirect.forward(request, response);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
			redirect.forward(request, response);
		}
	}

}
