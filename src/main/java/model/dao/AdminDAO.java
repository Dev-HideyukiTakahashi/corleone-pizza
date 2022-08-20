package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DatabaseConnection;

public class AdminDAO 
{
	
	private Connection connection = null;
	
	public AdminDAO() 
	{
		connection = DatabaseConnection.getConnection();
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
			
			while(rs.next()) {return true;} // Encontrou um usuário com mesmo login e senha informado
		}
		catch(SQLException e) 
		{
			System.out.println("Unexpected error in validate login: " + e.getMessage());
		}
		
		return false; // Não encontrou usuário com os dados informados
	}

}
