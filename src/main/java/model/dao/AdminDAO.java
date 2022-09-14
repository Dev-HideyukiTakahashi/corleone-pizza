package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.entities.Admin;

public class AdminDAO 
{
	
	private Connection connection = null;
	
	public AdminDAO() 
	{
		connection = DatabaseConnection.getPostgresSQLConnection();
	}
	
	public boolean validateLogin(String login, String password) 
	{
		try 
		{
			String sql = ("SELECT * FROM tb_admin "
						  +"WHERE login = ? "    // Busca em todo banco de dados para ver se 
						  +"AND senha = ?");     // login e senha existe
			
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, login);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {return true;} // Encontrou um usuario com mesmo login e senha informado
		}
		catch(SQLException e) 
		{
			System.out.println("Unexpected error in validate login: " + e.getMessage());
		}
		
		return false; // Nao encontrou usuario com os dados informados
	}
	
	// deletando usuario por id
	public void deleteUserId(Long id) throws SQLException {
		
		String sql = "DELETE FROM tb_admin WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.executeUpdate();
		
		connection.commit();
	}
	
	
	// inserindo novo usuario no banco
	public void insertUser(Admin user) {
		try {
			String sql = "INSERT INTO tb_admin(admin_name, phone, email, login, senha, partner)VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setString(1, user.getAdminName());
			ps.setString(2, user.getPhone());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getLogin());
			ps.setString(5, user.getPassword());
			ps.setString(6, user.getPartner());
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
	
	public Admin adminData(String login) 
	{
		
		Admin adminLogin = new Admin();
		try 
		{
			String sql = ("SELECT * FROM tb_admin "
						 +"WHERE login = ? ");

			
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, login);
			ResultSet rs = st.executeQuery();
			
			
			while(rs.next()) {
				adminLogin.setAdminName(rs.getString("admin_name"));
				adminLogin.setPhone(rs.getString("phone"));
				adminLogin.setEmail(rs.getString("email"));
				adminLogin.setLogin(rs.getString("login"));
				adminLogin.setPassword(rs.getString("senha"));
				adminLogin.setPartner(rs.getString("partner"));
				adminLogin.setId(rs.getLong("id"));
				adminLogin.setPhoto(rs.getString("photo"));
				adminLogin.setExtension(rs.getString("extension"));
			} 
		}
		catch(SQLException e) 
		{
			System.out.println("Unexpected error in generate admin data: " + e.getMessage());
		}
		
		return adminLogin;
	}

	public List<Admin> userSearchAll() throws SQLException {
		
		String sql 	 		 = "SELECT * FROM tb_admin";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs 		 = ps.executeQuery();
		
		List<Admin> users = new ArrayList<>();
		
		while(rs.next()) {
			Admin user  = new Admin();
			user.setAdminName(rs.getString("admin_name"));
			user.setPhone(rs.getString("phone"));
			user.setEmail(rs.getString("email"));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("senha"));
			user.setPartner(rs.getString("partner"));
			user.setId(rs.getLong("id"));
			
			users.add(user);
		}
		
		return users;
	}

	public Admin findUserId(Long id) throws SQLException {
		
		String sql = "SELECT * FROM tb_admin WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		
		Admin user = new Admin();
		while(rs.next()) {
			user.setAdminName(rs.getString("admin_name"));
			user.setPhone(rs.getString("phone"));
			user.setEmail(rs.getString("email"));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("senha"));
			user.setPartner(rs.getString("partner"));
			user.setId(rs.getLong("id"));
			user.setPhoto(rs.getString("photo"));
			user.setExtension(rs.getString("extension"));
		}
		
		return user;
	}

	public void updateUser(Admin newUser) {
		try {
			String sql = "UPDATE tb_admin SET admin_name=?, phone=?, email=?, login=?, senha=?, partner=? WHERE id = ?;";

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setString(1, newUser.getAdminName());
			ps.setString(2, newUser.getPhone());
			ps.setString(3, newUser.getEmail());
			ps.setString(4, newUser.getLogin());
			ps.setString(5, newUser.getPassword());
			ps.setString(6, newUser.getPartner());
			ps.setLong(7, newUser.getId());
			ps.executeUpdate();

			connection.commit();
			
			// Atualizando a foto do usuario
			if (newUser.getPhoto() != null && !newUser.getPhoto().isEmpty()) 
			{
				sql = "update tb_admin set photo =?, extension=? where id = ?";

				ps = connection.prepareStatement(sql);

				ps.setString(1, newUser.getPhoto());
				ps.setString(2, newUser.getExtension());
				ps.setLong(3, newUser.getId());

				ps.execute();

				connection.commit();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public boolean loginExists(String login) throws SQLException 
	{
	
		String sql   = "SELECT * FROM tb_admin WHERE login = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, login);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			return true;
		}
		return false;
	}
}
