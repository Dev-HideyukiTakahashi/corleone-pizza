package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	 * @param offset numero da pagina a ser exibida
	 * @return lista com dez elementos de acordo com a pagina
	 * @throws SQLException the SQL exception
	 */
	public List<Log> loadPage(Integer offset) throws SQLException 
	{
		List<Log> log  = new ArrayList<>();
		String    sql  = "SELECT * FROM tb_log ORDER BY date LIMIT 10 OFFSET " + offset;
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet	      rs = ps.executeQuery();
		
		while(rs.next()) {log.add(new Log(rs.getTimestamp("date"), rs.getString("field")));}
		
		return log;
	}
	
	/**
	 * Busca uma lista com todos logs de produtos alterados e qual usuario alterou.
	 * Filtra as datas de inicio e fim. 
	 * Inclui as datas extremas pela concatenacao do horario no servlet.
	 * 
	 * @param offset numero da pagina a ser exibida
	 * @param dateBegin data inicio
	 * @param dateFinal data final
	 * @return lista com dez elementos de acordo com a pagina
	 * @throws SQLException the SQL exception
	 */
	public List<Log> loadPageDate(String dateBegin, String dateFinal) throws SQLException 
	{
		List<Log> log = new ArrayList<>();
		String    sql = "SELECT * FROM tb_log "
					   +"WHERE date BETWEEN ? AND ?";

		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setTimestamp(1, Timestamp.valueOf(dateBegin));
		ps.setTimestamp(2, Timestamp.valueOf(dateFinal));
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {log.add(new Log(rs.getTimestamp("date"), rs.getString("field")));}
		
		return log;
	}
	
	/**
	 * Calcula quantas paginas vao ser exibidas de acordo com o numero de registros.
	 * A view de "Log", mostra 10 registros por pagina.
	 *
	 * @return numero de paginas na view
	 * @throws SQLException the SQL exception
	 */
	public Integer totalPages() throws SQLException 
	{
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
		if(user.getId() != 1) 
		{
			String 			  sql  = "INSERT INTO tb_log(date, field)	VALUES (?, ?)";
			String 			  log  = "Usuario: " + user.getUserName()+"\nIniciou a sessão.";
			LocalDateTime     date = LocalDateTime.now();
			PreparedStatement ps   = connection.prepareStatement(sql);
			
			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, Timestamp.valueOf(date));
			ps.setString(2, log);
			ps.execute();
			
			connection.commit();
		}
	}
	
	/**
	 * Registra no log horario que o usuario saiu do sistema.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void userLogout(User user) throws SQLException 
	{
		if(user.getId() != 1) 
		{
			String 			  sql  = "INSERT INTO tb_log(date, field)	VALUES (?, ?)";
			String 			  log  = "Usuario: " + user.getUserName()+"\nFinalizou a sessão.";
			LocalDateTime     date = LocalDateTime.now();
			PreparedStatement ps   = connection.prepareStatement(sql);
			
			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, Timestamp.valueOf(date));
			ps.setString(2, log);
			ps.execute();
			
			connection.commit();
		}
	}
}
