package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.entities.Client;
import servlets.ServletLogin;

public class ClientDAO {

	private Connection connection;
	

	public ClientDAO() {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}

	// Novo usuario
	public void insertClient(Client client, Long connectedId) {
		try {
			String sql = "INSERT INTO client(name, phone, email, adress, reference, admin_id) " + "VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setString(1, client.getName());
			ps.setString(2, client.getPhone());
			ps.setString(3, client.getEmail());
			ps.setString(4, client.getAdress());
			ps.setString(5, client.getReference());
			ps.setLong(6, connectedId);
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

	// Buscando usuario por ID
	public List<Client> clientSearch(String option, String field, Long connectedId) 
	{
		List<Client> clientFound = new ArrayList<>();
		try 
		{
			String sql = null;
			
			if(option.equals("nameOption") || option == "nameOption") 
			{
				field = field.toUpperCase();
				// Checando se o usuario logado e um admin
				// O admin busca todos os registros do BD, usuario apenas os seus registros de clientes
				sql =  (connectedId != 1) 
						? "SELECT * FROM client WHERE upper(name) LIKE (?) AND admin_id = ?" 
						: "SELECT * FROM client WHERE upper(name) LIKE (?)";
			}
			else
			{
				sql =  (connectedId != 1) 
						? "SELECT * FROM client WHERE phone LIKE (?) AND admin_id = ?" 
						: "SELECT * FROM client WHERE phone LIKE (?)";
			}
			
			ResultSet rs;
			if(connectedId == 1) 
			{
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, "%"+ field + "%");
				rs = ps.executeQuery();
			}
			else 
			{				
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, "%"+ field + "%");
				ps.setLong(2, connectedId);
				rs = ps.executeQuery();
			}

			while (rs.next()) 
			{
				clientFound.add(clientAssembler(rs));
				
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return clientFound;
	}
	
	//Buscando cliente por telefone
	public Client clientById(Long id) throws SQLException 
	{
		String sql 		     = "SELECT * FROM client WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet rs 	     = ps.executeQuery();
		
		Client client = new Client();
		while(rs.next()) {
			client = clientAssembler(rs);
		}
		
		return client;
	}
	
	// Buscando todos cliente para gerar a lista dinamica na pagina find.jsp
	public List<Client> clientSearchAll(Long connectedId) 
	{
		List<Client> clientFound = new ArrayList<>();
		try 
		{
			String sql;
			
			sql =  (connectedId != 1) 
					? "SELECT * FROM client WHERE admin_id = ?" 
					: "SELECT * FROM client";
			
			ResultSet rs; 	
			if(connectedId == 1) 
			{
				PreparedStatement ps = connection.prepareStatement(sql);
				rs = ps.executeQuery();
			}
			else 
			{				
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setLong(1, connectedId);
				rs = ps.executeQuery();
			}

			while (rs.next()) {
				clientFound.add(clientAssembler(rs));
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return clientFound;
	}
	
	
	public void clientDelete(String valueDelete) throws SQLException 
	{
		String sql = "DELETE FROM client WHERE phone LIKE (?)";
		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setString(1, "%"+ valueDelete.substring(3) + "%");
		ps.executeUpdate();
		
		connection.commit();
	}
	
	public void clientUpdate(Client client) throws SQLException 
	{
		String sql = "UPDATE client SET name= ?, phone= ?, email=?, adress=?, reference=? WHERE phone= ?";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		
		ps.setString(1, client.getName());
		ps.setString(2, client.getPhone());
		ps.setString(3, client.getEmail());
		ps.setString(4, client.getAdress());
		ps.setString(5, client.getReference());
		ps.setString(6, client.getPhone());
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
		assembler.setId(rs.getLong("id"));

		return assembler;
	}

}
