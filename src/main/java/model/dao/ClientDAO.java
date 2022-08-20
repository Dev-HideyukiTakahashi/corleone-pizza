package model.dao;

import java.sql.Connection;

import config.DatabaseConnection;

public class ClientDAO {
	
	private Connection connection;
	
	public ClientDAO() {
		connection = DatabaseConnection.getConnection();
	}
	

}
