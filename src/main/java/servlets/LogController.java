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
 * The Class LogController. mapped /log
 * 
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */

public class LogController extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The log DAO. */
	private LogDAO logDAO = new LogDAO();
       
    /**
     * Instantiates a new log controller.
     */
    public LogController() {
        super();
    }

	/**
	 * Busca todos registros de log no banco de dados e disponibiliza na view.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try 
		{
			String 	  page = request.getParameter("page");
			page 		   = page == null ? "0" : page;
			List<Log> logs = logDAO.loadPage(Integer.parseInt(page));
			
			request.setAttribute("pageSelect", Integer.parseInt(page));
			request.setAttribute("logs", logs);
			request.setAttribute("totalPages", logDAO.totalPages());
			request.getRequestDispatcher("/pages/log.jsp").forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
