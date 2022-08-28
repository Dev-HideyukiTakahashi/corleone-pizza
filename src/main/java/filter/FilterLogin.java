package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import config.DatabaseConnection;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/pages/*" })
public class FilterLogin extends HttpFilter {
       
	private static final long serialVersionUID = 1L;
	private Connection connection;
	
	public FilterLogin() {
        super();
    }

	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try 
		{
			
		// Validação de usuário logado, caso o mesmo tentar entrar em alguma página sem estar logado
			HttpServletRequest loginRequest = (HttpServletRequest) request;
			HttpSession session 			= loginRequest.getSession();
			String user 					= (String) session.getAttribute("adminLogin");
			
		// URL que tentou acessar sem estar logado
			String url 						= loginRequest.getServletPath();
			
			
			// Validar se existe usuário e se está tentando acessar uma página diferente da tela de login
			if(user == null && !url.equalsIgnoreCase("pages/login")) 
			{
				RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp?url=" + url);
				request.setAttribute("msg", "Por favor realize o login");
				redirect.forward(request, response);
				return; 	// Para a execução e redireciona para o login
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
	public void init(FilterConfig fConfig) throws ServletException {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}

}
