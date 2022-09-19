package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.entities.Log;

/**
 * The Class LogDAO.
 * 
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
public class LogDAO {
	
	/** The connection. */
	private Connection connection;

	/**
	 * Instancia conexao com postgreSQL
	 */
	public LogDAO() {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}
	
	/**
	 * Busca uma lista com todos logs de produtos alterados e qual usuario alterou.
	 *
	 * @return lista de todos logs
	 * @throws SQLException the SQL exception
	 */
	public List<Log> load() throws SQLException 
	{
		List<Log> log  		 = new ArrayList<>();
		String    sql 		 = "SELECT * FROM tb_log";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet	      rs = ps.executeQuery();
		
		while(rs.next()) {log.add(new Log(rs.getString("date"), rs.getString("field")));}
		
		return log;
	}
}
