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
 *  O Filter está responsavel pelo rollback
 */
@WebServlet(urlPatterns = {"/pages/login", "/ServletLogin"})
public class ServletLogin extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;
	
	// Classe utilitária para guardar o id de qual usuário está logado em sistema
	private ServletUtil connectedId = new ServletUtil();
	
	private AdminDAO adminDAO = new AdminDAO();
	
    public ServletLogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			
			// Configurando qual usuário está utilizando o sistema
			Boolean isAdmin = connectedId.getUserConnected(request) == 1? true : false;
			request.getSession().setAttribute("isAdmin", isAdmin);
			
			String action    = request.getParameter("action"); // Argumento vindo da página JSP
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
			
			// Carregando dados nos settings
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("settings")) {

				Admin userSettings = adminDAO.findUserId(connectedId.getUserConnected(request));
				
				request.setAttribute("userSettings", userSettings); 
				RequestDispatcher redireciona = request.getRequestDispatcher("pages/userdata.jsp");

				redireciona.forward(request, response);		
				
			}
			
			// Logout
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("logout")) 
			{
				request.getSession().invalidate(); // Termina a sessão do usuário
				RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
				redirect.forward(request, response);
			}
			
			// Listar todos usuários na tela
			// Enviando requisição com lista de todos os clientes
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
			if(connectedId == null) {
				e.printStackTrace();
				RequestDispatcher redirect = request.getRequestDispatcher("/endsession.jsp");
				redirect.forward(request, response);
			}else {
				e.printStackTrace();
				RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
				redirect.forward(request, response);
			}

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			// Requisição da página newuser submitUser()
			String newName 	     = request.getParameter("newName");
			String newPhone 	 = request.getParameter("newPhone");
			String newEmail 	 = request.getParameter("newEmail"); // Recuperando dados do form em newuser.jsp
			String newLogin	     = request.getParameter("newLogin");
			String oldPassword   = request.getParameter("oldPassword");
			String newPartner    = request.getParameter("newPartner");
			String newId 		 = request.getParameter("newId");
			
			String action		 = request.getParameter("action");
			

			// Novo usuario
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("insert") ) 
			{
				if(!adminDAO.loginExists(newLogin)) 
				{
					Admin newUser = new Admin(newName, newPhone, newEmail, newLogin, oldPassword, newPartner, null);
					adminDAO.insertUser(newUser);
					response.getWriter().write("registrado");
				}
			}
			
			// Atualizando usuario
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("update") ) 
			{
				
				String newPassword   = request.getParameter("newPassword");
				Admin settingsUser   = adminDAO.findUserId(Long.parseLong(newId));
				boolean passMeet 	 = adminDAO.validateLogin(settingsUser.getLogin(), oldPassword);
				
				if(newPassword == null) {
					Admin newUser = new Admin(newName, newPhone, newEmail, newLogin, oldPassword, newPartner, Long.parseLong(newId));
					adminDAO.updateUser(newUser);
					response.getWriter().write("atualizado");
				}
				
				if(passMeet && newPassword != null && !newPassword.isEmpty()) {

					Admin newUser = new Admin(newName, newPhone, newEmail, settingsUser.getLogin(), newPassword, newPartner, Long.parseLong(newId));
					adminDAO.updateUser(newUser);
					response.getWriter().write("atualizado");
				}
				else if (!passMeet && newPassword != null && !newPassword.isEmpty()) {
					response.getWriter().write("passNot");
				}
				System.out.println();
			}
			
			// Request de parâmetros da tela de login
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
					
					// Configurando qual usuário está utilizando o sistema
					Boolean isAdmin = adminLogin.getId() == 1? true : false;
					request.getSession().setAttribute("isAdmin", isAdmin);
					
					if(isAdmin) {
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
			else if(action == null || action.isEmpty())    // Não preencheu nenhum campo e clicou em login
			{
				RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp");
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
