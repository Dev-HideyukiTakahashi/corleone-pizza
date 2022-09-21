package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.entities.Client;

/**
 * The Class ClientDAO.
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
public class ClientDAO {

	/** Conexao com banco de dados para essa classe. */
	private Connection connection;

	/**
	 * Instancia nova conexao com banco de dados.
	 */
	public ClientDAO() {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}

	/**
	 * Cadastra novo cliente no banco de dados.
	 * O cliente eh visivel apenas para o usuario da sessao.
	 *
	 * @param client objeto cliente
	 * @param connectedId id do usuario de sessao
	 * @throws SQLException 
	 */
	public void insertClient(Client client, Long connectedId) throws SQLException 
	{
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
	}

	/**
	 * Busca um cliente no banco de dados por nome ou telefone
	 *
	 * @param option nome ou telefone
	 * @param field valor da busca
	 * @param connectedId id do usuario da sessao
	 * @return lista de clientes
	 * @throws SQLException 
	 */
	public List<Client> clientSearch(String option, String field, Long connectedId) throws SQLException 
	{
		List<Client> clientFound = new ArrayList<>();
		String sql = null;
		
		if(option.equals("nameOption") || option == "nameOption") 
		{
			field = field.toUpperCase();
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

		while (rs.next()) {clientFound.add(clientAssembler(rs));}
		return clientFound;
	}
	
	/**
	 * Busca cliente por ID
	 *
	 * @param id id do cliente
	 * @return dados do cliente
	 * @throws SQLException the SQL exception
	 */
	public Client clientById(Long id) throws SQLException 
	{
		String sql 		     = "SELECT * FROM client WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet rs 	     = ps.executeQuery();
		
		Client client = new Client();
		while(rs.next()) {client = clientAssembler(rs);}
		return client;
	}
	
	/**
	 * Busca a lista de todos cliente cadastrados pelo usuario da sessao
	 *
	 * @param connectedId id do usuario da sessao
	 * @param offset numero da pagina a ser exibida
	 * @return lista com dez elementos de acordo com a pagina
	 * @throws SQLException 
	 */
	public List<Client> clientSearchAll(Long connectedId, Integer offset) throws SQLException 
	{
		List<Client> clientList = new ArrayList<>();
		String sql;
		
		sql =  (connectedId != 1) 
				? "SELECT * FROM client WHERE admin_id = ? ORDER BY name LIMIT 10 OFFSET " + offset 
				: "SELECT * FROM client ORDER BY name LIMIT 10 OFFSET " + offset;
		
		ResultSet rs; 	
		if(connectedId == 1){
			PreparedStatement ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
		}
		else{				
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, connectedId);
			rs = ps.executeQuery();
		}

		while (rs.next()) {clientList.add(clientAssembler(rs));}
		return clientList;
	}
	
	/**
	 * Calcula quantas paginas vao ser exibidas de acordo com o numero de registros.
	 * A view de "Lista", mostra 10 registros por pagina.
	 * O Admin tem acesso a todos clientes no banco de dados.
	 * Usuario tem acesso apenas aos cliente que ele cadastrou.
	 *
	 * @return numero de paginas na view
	 * @throws SQLException the SQL exception
	 */
	public Integer totalPages(Long connectedId) throws SQLException 
	{
		String sql;
		
		sql =  (connectedId != 1) 
				? "SELECT COUNT(1) as total FROM client WHERE admin_id = ?" 
				: "SELECT COUNT(1) as total FROM client";
		
		ResultSet rs; 	
		
		if(connectedId == 1){
			PreparedStatement ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
		}
		else{				
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, connectedId);
			rs = ps.executeQuery();
		}
		
		Double result = 0.0;
		while(rs.next()) {result = rs.getDouble("total");}

		Double offset = result / 10;
		offset 		  = offset % 2 > 0 ? offset + 1 : offset;
		
		return offset.intValue();
	}
	
	/**
	 * Atualiza os dados do cliente
	 *
	 * @param client objeto client
	 * @throws SQLException the SQL exception
	 */
	public void clientUpdate(Client client) throws SQLException 
	{
		String sql = "UPDATE client SET name= ?, phone= ?, email=?, adress=?, reference=? WHERE id= ?";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		
		ps.setString(1, client.getName());
		ps.setString(2, client.getPhone());
		ps.setString(3, client.getEmail());
		ps.setString(4, client.getAdress());
		ps.setString(5, client.getReference());
		ps.setLong(6, client.getId());
		ps.executeUpdate();
		
		connection.commit();
	}

	/**
	 * Verifica se o cliente existe no banco de dados
	 *
	 * @param client objeto client
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	public boolean clientExists(Client client) throws SQLException 
	{
		String sql 	         = "SELECT * FROM client";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs	     = ps.executeQuery();

		while (rs.next()) {
			if (client.getPhone().equals(rs.getString("phone"))) {return true;}
		}
		return false;
	}

	/**
	 * Montador de client
	 *
	 * @param rs ResultSet com dados do banco de dados
	 * @return objeto client montado
	 * @throws SQLException the SQL exception
	 */
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
