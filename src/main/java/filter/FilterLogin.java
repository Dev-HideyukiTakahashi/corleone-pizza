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
	public void init(FilterConfig fConfig) throws ServletException {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}

}
