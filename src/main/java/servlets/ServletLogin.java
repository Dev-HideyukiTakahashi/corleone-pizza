package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		try
		{
			String action    = request.getParameter("action"); // Argumento vindo da p�gina JSP
			String idRequest = request.getParameter("idRequest");
			// Deletar usuario por id
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("delete"))
			{
				Long id = Long.parseLong(idRequest);
				adminDAO.deleteUserId(id);
			}
			
			
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("search")) // Buscar usuario por id
			{
				Long id	   = Long.parseLong(idRequest);
				Admin user = adminDAO.findUserId(id);
				
				ObjectMapper mapper = new ObjectMapper();
				String JSON 		= mapper.writeValueAsString(user);
				response.getWriter().write(JSON);
				
			}
			
			// Logout
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("logout")) 
			{
				request.getSession().invalidate(); // Termina a sess�o do usu�rio
				RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
				redirect.forward(request, response);
			}
			
			// Listar todos usu�rios na tela
			// Enviando requisi��o com lista de todos os clientes
			if (action != null && !action.isEmpty() && action.equalsIgnoreCase("searchList")) 
			{
				List<Admin> users = new ArrayList<>();
				users = adminDAO.userSearchAll();
				
				request.setAttribute("userData", users); 
				request.setAttribute("userDataSize", users.size()); 
				RequestDispatcher redireciona = request.getRequestDispatcher("pages/listuser.jsp");

				redireciona.forward(request, response);				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
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
			String newId 		 = request.getParameter("newId");
			
			String action		 = request.getParameter("action");
			
			// Novo usuario
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("insert") ) 
			{
				Admin newUser = new Admin(newName, newPhone, newEmail, newLogin, newPassword, newPartner, null);
				adminDAO.insertUser(newUser);
			}
			
			// Atualizando usuario
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("update") ) 
			{
				Admin newUser = new Admin(newName, newPhone, newEmail, newLogin, newPassword, newPartner, null);
				newUser.setId(Long.parseLong(newId));
				adminDAO.updateUser(newUser);
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
