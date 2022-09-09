package servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		
		String action = request.getParameter("action");
		
		if(action != null && !action.isEmpty() && action.equalsIgnoreCase("listAll"))
		{
			
			List<Motoboy> list = motoboyDAO.motoboySearchAll();
			request.setAttribute("motoboyData", list);
			RequestDispatcher redirect = request.getRequestDispatcher("/pages/motoboy/motoboy.jsp");
			redirect.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
