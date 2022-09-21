package model.dao;

import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import config.DatabaseConnection;
import model.entities.Log;
import model.entities.User;

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
		
		while(rs.next()) {log.add(new Log(rs.getTimestamp("date"), rs.getString("field")));}
		
		return log;
	}
	
	
	/**
	 * Busca uma lista com todos logs de produtos alterados e qual usuario alterou.
	 *
	 * @return lista de todos logs
	 * @throws SQLException the SQL exception
	 */
	public List<Log> loadPage(Integer offset) throws SQLException 
	{
		List<Log> log  		 = new ArrayList<>();
		String    sql 		 = "SELECT * FROM tb_log order by date limit 10 offset " + offset;
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet	      rs = ps.executeQuery();
		
		while(rs.next()) {log.add(new Log(rs.getTimestamp("date"), rs.getString("field")));}
		
		return log;
	}
	
	public Integer totalPages() throws SQLException 
	{
		Connection connection = DatabaseConnection.getPostgresSQLConnection();
		String 			  sql  = "SELECT COUNT(1) as total FROM tb_log";
		PreparedStatement ps   = connection.prepareStatement(sql);
		ResultSet		  rs   = ps.executeQuery();
		
		Double result = 0.0;
		while(rs.next()) {result = rs.getDouble("total");}

		Double offset = result / 10;
		offset 		  = offset % 2 > 0 ? offset + 1 : offset;
		
		return offset.intValue();
	}

	/**
	 * Registra no log horario que o usuario logou no sistema.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void userLogin(User user) throws SQLException 
	{
		String 			  sql  = "INSERT INTO tb_log(date, field)	VALUES (?, ?)";
		String 			  log  = user.getUserName()+" iniciou a sessão.";
		LocalDateTime     date = LocalDateTime.now();
		PreparedStatement ps   = connection.prepareStatement(sql);
		
		ps = connection.prepareStatement(sql);
		ps.setTimestamp(1, Timestamp.valueOf(date));
		ps.setString(2, log);
		ps.execute();
		
		connection.commit();
	}
	
	/**
	 * Registra no log horario que o usuario saiu do sistema.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void userLogout(User user) throws SQLException 
	{
		String 			  sql  = "INSERT INTO tb_log(date, field)	VALUES (?, ?)";
		String 			  log  = user.getUserName()+" encerrou a sessão.";
		LocalDateTime     date = LocalDateTime.now();
		PreparedStatement ps   = connection.prepareStatement(sql);
		
		ps = connection.prepareStatement(sql);
		ps.setTimestamp(1, Timestamp.valueOf(date));
		ps.setString(2, log);
		ps.execute();
		
		connection.commit();
	}
}
