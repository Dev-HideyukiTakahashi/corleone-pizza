package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.LogDAO;
import model.entities.Log;


// TODO: Auto-generated Javadoc
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
	
	private boolean isDate = false;
	
	private List<Log> logs = new ArrayList<>();
       
    /**
     * Instantiates a new log controller.
     */
    public LogController() {
        super();
    }

	/**
	 * Busca todos registros de log no banco de dados e disponibiliza na view.
	 * Condicional para manter a paginacao de acordo com a view no momento (data ou normal)
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try 
		{
			String action  = request.getParameter("action");
			String page    = request.getParameter("page");
			
			if(page != null && action == null && isDate == false) {
				listAll(request, response, page);
			}
			else if(page != null)  {
				listDate(request, response, page);
			}
			
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("listAll")){
				listAll(request, response, page);
				isDate = false;
			}
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("listDate")){
				listDate(request, response, page);
				isDate = true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}


	/**
	 * Lista todos os logs em sistema, carrega automaticamente com a chamada da view.
	 *
	 * @param request the request
	 * @param response the response
	 * @param page the page
	 * @throws NumberFormatException the number format exception
	 * @throws SQLException the SQL exception
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void listAll(HttpServletRequest request, HttpServletResponse response, String page) throws NumberFormatException, SQLException, ServletException, IOException 
	{
		page 		   = page == null ? "0" : page;
		List<Log> logs = logDAO.loadPage(Integer.parseInt(page));
		
		request.setAttribute("numberPage", Integer.parseInt(page));
		request.setAttribute("logs", logs);
		request.setAttribute("totalPages", logDAO.totalPages());
		request.getRequestDispatcher("/pages/log.jsp").forward(request, response);
	}
	
	
	/**
	 * Lista todos os logs em sistema.
	 * Resultado de acordo com filtro datas.
	 * Timestamp espera o formato time, concatenado horarios para incluir as datas extremas.
	 *
	 * @param request the request
	 * @param response the response
	 * @param page the page
	 * @throws NumberFormatException the number format exception
	 * @throws SQLException the SQL exception
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void listDate(HttpServletRequest request, HttpServletResponse response, String page) throws NumberFormatException, SQLException, ServletException, IOException 
	{
		page = page == null ? "0" : page;
		String dateBegin = request.getParameter("dateBegin");
		String dateFinal = request.getParameter("dateFinal");
		
		List<Log> logsView = new ArrayList<>();
		
		if(dateBegin != null && dateFinal != null) {
			dateBegin = dateBegin.concat(" 00:00:00");
			dateFinal = dateFinal.concat(" 23:59:59");
			
			logs = logDAO.loadPageDate(dateBegin, dateFinal);
			
			for(int i = Integer.parseInt(page); i < 10; i++) {
				if(i == logs.size()) {break;}
				logsView.add(logs.get(i));
			}
		}
		else if(Integer.parseInt(page) == 0) {
			for(int i = Integer.parseInt(page); i < 10; i++) 
				logsView.add(logs.get(i));
		}
		else {
			for(int i = Integer.parseInt(page); i < (Integer.parseInt(page) + 10); i++) {
				if(i == logs.size()) {break;}
				logsView.add(logs.get(i));
			}
		}
		
		double result = (logs.size() - 1);
		Double offset = (result / 10);
		offset 		  = offset % 2 > 0 ? offset + 1 : offset;
		
		request.setAttribute("numberPage", Integer.parseInt(page));
		request.setAttribute("logs", logsView);
		request.setAttribute("totalPages", offset.intValue());
		request.getRequestDispatcher("/pages/log.jsp").forward(request, response);
	}
}
