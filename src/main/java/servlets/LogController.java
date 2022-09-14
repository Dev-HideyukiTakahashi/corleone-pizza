package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.LogDAO;
import model.entities.Log;

/**
 * Mapped /log
 */
public class LogController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private LogDAO logDAO = new LogDAO();
       
    public LogController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try 
		{
			List<Log> logs = logDAO.load();
			
			request.setAttribute("logs", logs);
			
			RequestDispatcher redirect = request.getRequestDispatcher("/pages/log.jsp");
			redirect.forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}

	}

}
