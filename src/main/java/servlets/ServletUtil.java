package servlets;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.dao.UserDAO;

/**
 * 
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
public class ServletUtil {

	/** The user DAO. */
	private UserDAO userDAO = new UserDAO();
	
	/**
	 * Retorna o usuario conectado na sessao
	 *
	 * @param request the request
	 * @return usuario conectado na sessao
	 * @throws SQLException the SQL exception
	 */
	public Long getUserConnected(HttpServletRequest request) throws SQLException 
	{
		HttpSession session = request.getSession();

		String userConnected = (String) session.getAttribute("userLogin");
		
		return userDAO.userData(userConnected).getId();
	}
}
