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
 *  O Filter está responsavel pelo rollback
 */
@WebServlet(urlPatterns = {"/pages/login", "/ServletLogin"})
public class ServletLogin extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;
	
	private AdminDAO adminDAO = new AdminDAO();
	
	private static Long idAdmin = null;
       
    public ServletLogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action"); // Argumento vindo da 'navbar / Log out'
		
		if(action != null && !action.isEmpty() && action.equalsIgnoreCase("logout")) 
		{
			request.getSession().invalidate(); // Termina a sessão do usuário
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
			String login     = request.getParameter("login");
			String password  = request.getParameter("password");		
			String url       = request.getParameter("url");
			
			
			// Validando se os dados foram preenchidos antes de acessar o sistema
			if(login != null && !login.isEmpty() && password != null && !password.isEmpty()) 
			{
				
				// Comparando os dados preenchidos com o banco de dados
				if(adminDAO.validateLogin(login, password))
				{
					// Usuario e senha confirmados com BD, inicia a sessão com os dados do usuario logado
					Admin adminLogin = adminDAO.adminData(login);
					request.getSession().setAttribute("adminName", adminLogin.getAdminName());
					request.getSession().setAttribute("adminLogin", adminLogin.getLogin());
					
					// Configurando qual usuário está utilizando o sistema, o método get está no final do código
					idAdmin = adminLogin.getId();
					
					if(idAdmin == 1) {
						request.getSession().setAttribute("adminOffice", "Administrador");
						// Poderia ter um identificador no banco de dados para foto de perfil
						request.getSession().setAttribute("adminImg", "/assets/images/faces/face.jpg");
					}
					else {
						request.getSession().setAttribute("adminOffice", "Usuário");
						// Poderia ter um identificador no banco de dados para foto de perfil
						request.getSession().setAttribute("adminImg", "/assets/images/faces/face2.jpg");
					}
					
					// Se o usuário não tentou acessar nenhuma page antes da tela de login redirecionar a main
					if(url == null || url.equals("null"))
					{
						url = "pages/main.jsp";
					}
					
					// Se o usuário tentou acessar alguma página sem logar, após o login com sucesso o mesmo acessa a página desejada
					RequestDispatcher redirect = request.getRequestDispatcher(url);
					redirect.forward(request, response);
				}
				else  // Após busca em banco de dados, não encotra usuário ou senha / ou ambos
				{
					RequestDispatcher redirecionador = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Usuario ou senha inválido");
					redirecionador.forward(request, response);
				}
			}
			else     // Não preencheu nenhum campo e clicou em login
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

	public static Long getIdAdmin() {
		return idAdmin;
	}
	
}
