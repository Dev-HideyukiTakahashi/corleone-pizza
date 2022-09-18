package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.entities.User;

/**
 * Classe UserDAO.
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */

public class UserDAO 
{
	
	/** Conexao com banco de dados para essa classe. */
	private Connection connection = null;
	
	/**
	 * Instancia nova conexao com banco de dados.
	 */
	public UserDAO() 
	{
		connection = DatabaseConnection.getPostgresSQLConnection();
	}
	
	/**
	 * Valida se os campos existem no banco de dados e se a senha/login estao corretos.
	 *
	 * @param login login do usuario
	 * @param password password do usuario
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	public boolean validateLogin(String login, String password) throws SQLException 
	{
		String sql = ("SELECT * FROM tb_user "
					  +"WHERE login = ? "    
					  +"AND senha = ?");     
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, login);
		st.setString(2, password);
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {return true;} 
		
		return false; 
	}
	
	/**
	 * Deleta um usuario do banco de dados por ID.
	 *
	 * @param id id do usuario
	 * @throws SQLException the SQL exception
	 */
	public void deleteUserById(Long id) throws SQLException 
	{
		String sql = "DELETE FROM tb_user WHERE id = ?";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.executeUpdate();
		
		connection.commit();
	}
	
	
	/**
	 * Inserir um novo usuario no banco de dados.
	 *
	 * @param user usuario
	 * @throws SQLException the SQL exception
	 */
	public void insertUser(User user) throws SQLException 
	{
		String sql = "INSERT INTO tb_user(user_name, phone, email, login, senha, partner)VALUES (?, ?, ?, ?, ?, ?)";
	
		PreparedStatement ps = connection.prepareStatement(sql);
	
		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPhone());
		ps.setString(3, user.getEmail());
		ps.setString(4, user.getLogin());
		ps.setString(5, user.getPassword());
		ps.setString(6, user.getPartner());
		ps.execute();
	
		connection.commit();
	}
	
	/**
	 * Recupera os dados de determinado usuario por login.
	 *
	 * @param login login do usuario para busca
	 * @return dados do usuario
	 * @throws SQLException the SQL exception
	 */
	public User userData(String login) throws SQLException 
	{
		User userLogin = new User();
		String sql = ("SELECT * FROM tb_user "
					 +"WHERE login = ? ");
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, login);
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {userLogin = userAssembler(rs);} 
	
		return userLogin;
	}

	/**
	 * Busca todos registros de usuarios no banco de dados.
	 *
	 * @return lista com todos usuarios
	 * @throws SQLException the SQL exception
	 */
	public List<User> userFindAll() throws SQLException 
	{
		String sql 	 		 = "SELECT * FROM tb_user";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs 		 = ps.executeQuery();
		List<User> users 	 = new ArrayList<>();
		
		while(rs.next()){users.add(userAssembler(rs));}
		return users;
	}

	/**
	 * Busca registro de usuario por ID no banco de dados
	 *
	 * @param id id do usuario
	 * @return dados do usuario
	 * @throws SQLException the SQL exception
	 */
	public User findUserById(Long id) throws SQLException 
	{
		String sql = "SELECT * FROM tb_user WHERE id = ?";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		
		ResultSet rs = ps.executeQuery();
		User user    = new User();
		while(rs.next()) {user = userAssembler(rs);}
		
		return user;
	}

	/**
	 * Atualiza os dados do usuario no banco de dados.
	 * Se o usuario atualizar a foto no perfil, os campos de foto sao atualizados de forma separada
	 *
	 * @param user the user
	 * @throws SQLException the SQL exception
	 */
	public void updateUser(User user) throws SQLException 
	{
		String sql = "UPDATE tb_user SET user_name=?, phone=?, email=?, login=?, senha=?, partner=? WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPhone());
		ps.setString(3, user.getEmail());
		ps.setString(4, user.getLogin());
		ps.setString(5, user.getPassword());
		ps.setString(6, user.getPartner());
		ps.setLong(7, user.getId());
		ps.executeUpdate();

		connection.commit();
		
		if (user.getPhoto() != null && !user.getPhoto().isEmpty()) 
		{
			sql = "update tb_user set photo =?, extension=? where id = ?";
			ps  = connection.prepareStatement(sql);
			
			ps.setString(1, user.getPhoto());
			ps.setString(2, user.getExtension());
			ps.setLong(3, user.getId());

			ps.execute();
			connection.commit();
		}
	}
	
	
	/**
	 * Valida se o login existe no banco de dados
	 *
	 * @param login login do usuario
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	public boolean loginExists(String login) throws SQLException 
	{
		String sql   = "SELECT * FROM tb_user WHERE login = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, login);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {return true;}
		return false;
	}
	
	/**
	 * Metodo construtor de usuario
	 *
	 * @param rs ResultSet com resultados da busca no banco de dados
	 * @return dados do usuario
	 * @throws SQLException the SQL exception
	 */
	private User userAssembler(ResultSet rs) throws SQLException
	{
		User user = new User();
		user.setUserName(rs.getString("user_name"));
		user.setPhone(rs.getString("phone"));
		user.setEmail(rs.getString("email"));
		user.setLogin(rs.getString("login"));
		user.setPassword(rs.getString("senha"));
		user.setPartner(rs.getString("partner"));
		user.setId(rs.getLong("id"));
		user.setPhoto(rs.getString("photo"));
		user.setExtension(rs.getString("extension"));
		
		return user;
	}
}
