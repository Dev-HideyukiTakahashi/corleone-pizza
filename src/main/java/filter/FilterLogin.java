package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import config.DatabaseConnection;


/**
 * The Class FilterLogin.
 * 
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
@WebFilter(urlPatterns = { "/pages/*" }) 
public class FilterLogin extends HttpFilter {
       
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The connection. */
	private Connection connection;
	
	/**
	 * Instantiates a new filter login.
	 */
	public FilterLogin() {
        super();
    }

	/**
	 * Fecha conexao com banco de dados.
	 */
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * O filter nao permite acessar nenhuma pagina da da pasta webapp/pages sem estar logado
	 *
	 * @param request the request
	 * @param response the response
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try 
		{
			HttpServletRequest loginRequest = (HttpServletRequest) request;
			HttpSession session 			= loginRequest.getSession();
			String user 					= (String) session.getAttribute("userLogin");
			String url 						= loginRequest.getServletPath();
			
			if(user == null && !url.equalsIgnoreCase("pages/login")) 
			{
				RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp?url=" + url);
				request.setAttribute("msg", "Por favor realize o login");
				redirect.forward(request, response);
				return; 	
			}
			else {
				chain.doFilter(request, response);
			}
			connection.commit();
		}

		
		catch(Exception e) 
		{
			e.printStackTrace();
			RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
			redirect.forward(request, response);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Abre a conexao com banco de dados.
	 *
	 * @param fConfig the f config
	 * @throws ServletException the servlet exception
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}
}
