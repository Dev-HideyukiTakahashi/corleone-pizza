package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.LogDAO;
import model.dao.UserDAO;
import model.entities.User;

/**
 * The Class ServletLogin. mapped /login
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */


@MultipartConfig 
@WebServlet(urlPatterns = {"/pages/login", "/ServletLogin"})
public class ServletLogin extends HttpServlet 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Classe utilitaria para recuperar o id do usuario da sessao. */
	private ServletUtil connectedId = new ServletUtil();
	
	/** The user DAO. */
	private UserDAO userDAO 		= new UserDAO();
	
	/** The user DAO. */
	private LogDAO logDAO 		    = new LogDAO();
	
    /**
     * Instantiates a new servlet login.
     */
    public ServletLogin() {
        super();
    }

	/**
	 * Ao conectar na app, verifica se o usuario logado eh o ID:1(admin do sistema).
	 * Baseado na action, o doGet() faz alguma chamada.
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Boolean isAdmin = null;
		try
		{
			isAdmin = connectedId.getUserConnected(request) == 1? true : false;
			request.getSession().setAttribute("isAdmin", isAdmin);
			
			String action    = request.getParameter("action"); 
			String idRequest = request.getParameter("idRequest");
			
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("delete")){
				deleteAction(idRequest);
			}			
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("search")){
				searchAction(idRequest, response);
			}			
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("settings")) {
				settingsAction(request, response);
			}
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("logout")){
				logoutAction(request, response);
			}
			else if (action != null && !action.isEmpty() && action.equalsIgnoreCase("searchList")){
				searchListAction(request, response);
			}
		}
		catch(Exception e){
			if(isAdmin == null) {
				request.getRequestDispatcher("/endsession.jsp").forward(request, response);
			}else {
				e.printStackTrace();
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}
	}

	/**
	 * Deleta um usuario por ID.
	 *
	 * @param id id do usuario
	 * @throws NumberFormatException the number format exception
	 * @throws SQLException the SQL exception
	 */
	private void deleteAction(String id) throws NumberFormatException, SQLException{
		userDAO.deleteUserById(Long.parseLong(id));
	}

	/**
	 * Busca um usuario por ID.
	 *
	 * @param id id do usuario
	 * @param response the response
	 * @throws NumberFormatException the number format exception
	 * @throws SQLException the SQL exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void searchAction(String id, HttpServletResponse response) throws NumberFormatException, SQLException, IOException 
	{
		User user = userDAO.findUserById(Long.parseLong(id));
		
		ObjectMapper mapper = new ObjectMapper();
		String JSON 		= mapper.writeValueAsString(user);
		response.getWriter().write(JSON);
	}

	/**
	 * Chamada na view de configuracoes do usuario, responsavel pela customizacao dos dados do usuario.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws SQLException the SQL exception
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void settingsAction(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException 
	{
		User userSettings = userDAO.findUserById(connectedId.getUserConnected(request));
		
		request.setAttribute("userSettings", userSettings); 
		RequestDispatcher redirect = request.getRequestDispatcher("pages/users/userdata.jsp");
		redirect.forward(request, response);		
	}
	
	/**
	 * Logout action, finaliza sessao.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException 
	 */
	private void logoutAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException 
	{
		logDAO.userLogout(userDAO.findUserById(connectedId.getUserConnected(request)));
		request.getSession().invalidate(); 
		RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
		redirect.forward(request, response);
	}
	
	/**
	 * Retorna uma lista com todos os dados dos usuarios cadastrados(apenas admin tem acesso).
	 *
	 * @param request the request
	 * @param response the response
	 * @throws SQLException the SQL exception
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void searchListAction(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException 
	{
		List<User> users = new ArrayList<>();
		users = userDAO.userFindAll();
		request.setAttribute("userData", users); 
		request.setAttribute("userDataSize", users.size()); 
		RequestDispatcher redireciona = request.getRequestDispatcher("pages/users/listuser.jsp");

		redireciona.forward(request, response);		
	}
	
	
	/**
	 * Validacao se usuario existe no banco de dados e senha/login estao corretos.
	 * Atualiza os dados de customizacao dos usuarios.
	 * Insere um novo usuario(apenas admin).
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			String login     = request.getParameter("login");
			String password  = request.getParameter("password");		
			String url       = request.getParameter("url");
			
			if(login != null && !login.isEmpty() && password != null && !password.isEmpty())
			{
				if(userDAO.validateLogin(login, password)){
					
					userViewConfig(request, response, login, url);
					
					if(request.getSession().getAttribute("sessionLogin") == null) {
						
						logDAO.userLogin(userDAO.findUserById(connectedId.getUserConnected(request)));
						request.getSession().setAttribute("sessionLogin", true);
					}
				}
				else{
					request.setAttribute("msg", "Usuario ou senha inválido");
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				}
			}
			
			String newName 	     = request.getParameter("newName");
			String newPhone 	 = request.getParameter("newPhone");
			String newEmail 	 = request.getParameter("newEmail"); 
			String newLogin	     = request.getParameter("newLogin");
			String oldPassword   = request.getParameter("oldPassword");
			String newPartner    = request.getParameter("newPartner");
			String newId 		 = request.getParameter("newId");
			
			String action		 = request.getParameter("action");

			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("insert") ) 
			{
				if(!userDAO.loginExists(newLogin)){
					User newUser = new User(newName, newPhone, newEmail, newLogin, oldPassword, newPartner, null);
					userDAO.insertUser(newUser);
				}
			}
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("update") ) 
			{
				String newPassword   = request.getParameter("newPassword");
				User settingsUser    = userDAO.findUserById(Long.parseLong(newId));
				boolean passMeet 	 = userDAO.validateLogin(settingsUser.getLogin(), oldPassword);
		
				if(passMeet) {

					User newUser = new User(newName, newPhone, newEmail, settingsUser.getLogin(), newPassword, newPartner, Long.parseLong(newId));

					if(request.getPart("filePhoto") != null) 
					{
						Part part   = request.getPart("filePhoto"); 
						if(part.getSize() > 0) { 
							byte[] foto = IOUtils.toByteArray(part.getInputStream()); 
							@SuppressWarnings("static-access")
							String imagemBase64 = "data:" + part.getContentType() + ";base64,"+ new Base64().encodeBase64String(foto); 
							
							newUser.setPhoto(imagemBase64);
							newUser.setExtension(part.getContentType().split("\\/")[1]); 
							request.getSession().setAttribute("userPhoto", newUser.getPhoto());
						}
					}
					userDAO.updateUser(newUser);
					request.getRequestDispatcher("pages/main.jsp").forward(request, response);
				}
				else{
					request.setAttribute("msg", "Senha inválida!");
					settingsAction(request,response);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	/**
	 * Carrega os dados do usuario logado na view, sidebar/navbar.
	 * Redireciona para pagina que tentou acessar sem estar logado.
	 *
	 * @param request the request
	 * @param response the response
	 * @param login login do usuario da sessao
	 * @param url url que tentou acessar sem logar
	 * @throws SQLException the SQL exception
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void userViewConfig(HttpServletRequest request, HttpServletResponse response, String login, String url) throws SQLException, ServletException, IOException 
	{
		User userLogin = userDAO.userData(login);
		request.getSession().setAttribute("userName", userLogin.getUserName());
		request.getSession().setAttribute("userLogin", userLogin.getLogin());
		request.getSession().setAttribute("userPhoto", userLogin.getPhoto());
				
		Boolean isAdmin = userLogin.getId() == 1? true : false;
		request.getSession().setAttribute("isAdmin", isAdmin);
		
		if(isAdmin) {
			request.getSession().setAttribute("adminOffice", "Administrador");
		}
		else{
			request.getSession().setAttribute("adminOffice", "Usuário");
		}

		if(url == null || url.equals("null"))		{
			url = "/pages/main.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
}
