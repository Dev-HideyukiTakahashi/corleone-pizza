package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.entities.Log;

public class LogDAO {
	
	private Connection connection;

	public LogDAO() {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}
	
	// Retorna todos registros de alterações nos nomes de produtos
	public List<Log> load() throws SQLException 
	{
		List<Log> log  		 = new ArrayList<>();
		String    sql 		 = "SELECT * FROM tb_log";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet	      rs = ps.executeQuery();
		
		while(rs.next()) {
			log.add(new Log(rs.getString("date"), rs.getString("field")));
		}
		
		return log;
		
	}

}
