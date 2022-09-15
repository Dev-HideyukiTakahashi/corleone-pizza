package servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.dao.AdminDAO;

public class ServletUtil {

	private AdminDAO adminDAO = new AdminDAO();
	
	public Long getUserConnected(HttpServletRequest request) 
	{
		HttpSession session = request.getSession();

		String userConnected = (String) session.getAttribute("adminLogin");
		
		return adminDAO.adminData(userConnected).getId();
	}
	

}
