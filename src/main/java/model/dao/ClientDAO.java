package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.entities.Client;

public class ClientDAO {

	private Connection connection;

	public ClientDAO() {
		connection = DatabaseConnection.getConnection();
	}

	public void insertClient(Client client) {
		try {
			String sql = "INSERT INTO client(name, phone, email, adress, reference) " + "VALUES (?, ?, ?, ?, ?)";

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setString(1, client.getName());
			ps.setString(2, client.getPhone());
			ps.setString(3, client.getEmail());
			ps.setString(4, client.getAdress());
			ps.setString(5, client.getReference());
			ps.execute();

			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public List<Client> clientSearch(String option, String field) 
	{
		List<Client> clientFound = new ArrayList<>();
		try 
		{
			String sql = null;
			if(option.equals("nameOption") || option == "nameOption") {
				field = field.toUpperCase();
				sql = "SELECT * FROM client WHERE upper(name) LIKE concat('%', ?, '%')";
			}
			else{
				sql = "SELECT * FROM client WHERE phone = ?";
			}
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, field);
			ResultSet rs 	     = ps.executeQuery();

			while (rs.next()) {
				clientFound.add(clientAssembler(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientFound;
	}
	
	public void clientDelete(String valueDelete) throws SQLException 
	{
		String sql = "DELETE FROM client WHERE phone = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, valueDelete);
		ps.executeUpdate();
		
		connection.commit();
	}

	public boolean clientExists(Client client) throws SQLException 
	{

		String sql 	         = "SELECT * FROM client";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs	     = ps.executeQuery();

		while (rs.next()) {
			if (client.getPhone().equals(rs.getString("phone").trim())) {
				return true;
			}
		}
		return false;
	}

	private Client clientAssembler(ResultSet rs) throws SQLException 
	{
		Client assembler = new Client();

		assembler.setName(rs.getString("name"));
		assembler.setPhone(rs.getString("phone"));
		assembler.setEmail(rs.getString("email"));
		assembler.setAdress(rs.getString("adress"));
		assembler.setReference(rs.getString("reference"));

		return assembler;
	}

}
