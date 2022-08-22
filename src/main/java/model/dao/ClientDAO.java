package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DatabaseConnection;
import model.entities.Client;

public class ClientDAO {
	
	private Connection connection;
	
	public ClientDAO() {
		connection = DatabaseConnection.getConnection();
	}
	
	public void insertClient(Client client) throws SQLException {
		
		String sql = "INSERT INTO client(name, phone, email, adress, reference) "
					+"VALUES (?, ?, ?, ?, ?);";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		
		ps.setString(1, client.getName());
		ps.setString(2, client.getPhone());
		ps.setString(3, client.getEmail());
		ps.setString(4, client.getAdress());
		ps.setString(5, client.getReference());		
		ps.execute();
		
		connection.commit();
	}
	
	
	public boolean clientExists(Client client) throws SQLException {
		
		Client dataBaseClient = new Client();
		
		String sql = "SELECT * FROM client";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			if(client.getPhone().trim().equalsIgnoreCase(rs.getString("phone").trim())) {
				return true;
			}
		}
		return false;
	}
	

}
