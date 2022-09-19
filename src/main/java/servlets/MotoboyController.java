package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.MotoboyDAO;
import model.entities.Motoboy;

/**
 * The Class MotoboyController. mapped /motoboy
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */

public class MotoboyController extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The motoboy DAO. */
	private MotoboyDAO motoboyDAO = new MotoboyDAO();
       
    /**
     * Instantiates a new motoboy controller.
     */
    public MotoboyController() {
        super();
    }
	
	/**
	 * Lista todos motoboys do banco de dados.
	 * Deleta motoboy por nome.
	 * Atualiza dados do motoboy.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			String action = request.getParameter("action");
			
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("listAll")){
				listAll(request, response);
			}
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("del"))
			{
				String motoboyName = request.getParameter("name");

				motoboyDAO.deleteByName(motoboyName);
				listAll(request, response);
			}
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("update")){
				update(request, response);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	/**
	 * Busca todos registros de motoboy no banco de dados para mostrar na view.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void listAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<Motoboy> list = motoboyDAO.motoboySearchAll();
		
		request.setAttribute("motoboyData", list);
		RequestDispatcher redirect = request.getRequestDispatcher("/pages/motoboy/motoboy.jsp");
		redirect.forward(request, response);
		
	}
	
	/**
	 * Atualiza os dados do motoboy via Ajax.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws SQLException the SQL exception
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws SQLException 
	{
		String type  = request.getParameter("type");
		String value = request.getParameter("value");
		String id  	 = request.getParameter("id");
		
		motoboyDAO.motoboyUpdate(type, value, id);
	}
	
	
	/**
	 * Cadastra um novo registro de motoboy no banco de dados via Ajax.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try 
		{
			String name  = request.getParameter("name");
			String phone  = request.getParameter("phone");
			String adress = request.getParameter("adress");
			
			
			motoboyDAO.motoboyInsert(name, phone, adress);
		}
		catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}
	}
}
