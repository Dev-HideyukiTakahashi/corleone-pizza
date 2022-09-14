package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.MotoboyDAO;
import model.entities.Motoboy;

/**
 * 		/motoboy
 */
public class MotoboyController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private MotoboyDAO motoboyDAO = new MotoboyDAO();
       
    public MotoboyController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			String action = request.getParameter("action");
			
			if(action != null && !action.isEmpty() && action.equalsIgnoreCase("listAll"))
			{
				
				List<Motoboy> list = motoboyDAO.motoboySearchAll();
				
				request.setAttribute("motoboyData", list);
				RequestDispatcher redirect = request.getRequestDispatcher("/pages/motoboy/motoboy.jsp");
				redirect.forward(request, response);
			}
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("del"))
			{
				String motoboyName = request.getParameter("name");

				motoboyDAO.deleteByName(motoboyName);
				
				List<Motoboy> list = motoboyDAO.motoboySearchAll();
				request.setAttribute("motoboyData", list);
				RequestDispatcher redirect = request.getRequestDispatcher("/pages/motoboy/motoboy.jsp");
				redirect.forward(request, response);
			}
			else if(action != null && !action.isEmpty() && action.equalsIgnoreCase("update"))
			{
				String type  = request.getParameter("type");
				String value = request.getParameter("value");
				String name  = request.getParameter("name");
				
				motoboyDAO.motoboyUpdate(type, value, name);
				
				List<Motoboy> list = motoboyDAO.motoboySearchAll();
				request.setAttribute("motoboyData", list);
				RequestDispatcher redirect = request.getRequestDispatcher("/pages/motoboy/motoboy.jsp");
				redirect.forward(request, response);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			RequestDispatcher redirecionador = request.getRequestDispatcher("/error.jsp");
			redirecionador.forward(request, response);
		}
	}

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
