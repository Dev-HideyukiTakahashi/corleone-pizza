package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import config.DatabaseConnection;
import model.entities.Product;
import model.entities.User;

/**
 * The Class ProductDAO.
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
public class ProductDAO {

	/** The connection. */
	private Connection connection;

	/**
	 * Instantiates a new product DAO.
	 */
	public ProductDAO() {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}
	
	/**
	 * Busca uma lista de produtos de acordo com o tipo (Pizza ou Drink).
	 *
	 * @param prodType tipo de produto
	 * @return lista do produto
	 * @throws SQLException the SQL exception
	 */
	public List<Product> productSearch(String prodType) throws SQLException 
	{
		List<Product> itemFound = new ArrayList<>();
		String sql = null;
		if(prodType.equals("pizza") || prodType == "pizza") {
			sql = "SELECT * FROM products WHERE type_item = 'Pizza'";
		}
		if(prodType.equals("drink") || prodType == "drink") {
			sql = "SELECT * FROM products WHERE type_item = 'Drink'";
		}
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs 	     = ps.executeQuery();

		while (rs.next()) {
			itemFound.add(productAssembler(rs));
		}
		return itemFound;
	}
	
	/**
	 * Busca um produto por codigo.
	 *
	 * @param prodCode codigo do produto
	 * @return the dados do produto
	 * @throws SQLException the SQL exception
	 */
	public Product productByCode(String prodCode) throws SQLException 
	{
		Product item = new Product();
		String sql = "SELECT * FROM products WHERE code = ?";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, Long.parseLong(prodCode));
		ResultSet rs 	     = ps.executeQuery();

		while (rs.next()) {
			item = (productAssembler(rs));
		}
		return item;
	}
	
	/**
	 * Atualiza um produto por codigo.
	 * Apenas Admin pode atualizar preco e descricao.
	 * Se algum usuario atualizar o nome, fica registrado no log.
	 *
	 * @param code codigo do produto
	 * @param value valor a ser alterado
	 * @param option campo a ser alterado
	 * @param user usuario da sessao
	 * @throws SQLException the SQL exception
	 */
	public void productUpdate(String code, String value, String option, User user) throws SQLException 
	{
		String sql   = null;
		if(option.equals("updateDescription") || option == "updateDescription") {
			sql = "UPDATE products SET description=? WHERE code=?";
		}
		else if(option.equals("updatePrice") || option == "updatePrice") {
			sql = "UPDATE products SET price=? WHERE code=?";
			value = value.replace(",", ".").replace("-", ".");
		}
		else if(option.equals("updateName") || option == "updateName") {
			sql = "UPDATE products SET item=? WHERE code=?";
		}
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, value);
		ps.setLong(2, Long.parseLong(code));
		ps.executeUpdate();
		
		connection.commit();
		
		if(user != null && user.getId() != 1) 
		{
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			
			LocalDateTime date = LocalDateTime.now();
			String log = "Usuário '"+user.getUserName()+"', alterou dados do produto:\nCódigo: "
						+ code +"\nNome alterado: '" + value + "'";
			
			sql = "INSERT INTO tb_log(date, field)	VALUES (?, ?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, dateFormat.format(date));
			ps.setString(2, log);
			ps.execute();
			connection.commit();
	    }
	}
	
	
	/**
	 * Registra um novo produto no banco de dados.
	 * Apenas Admin pode registrar um novo produto. 
	 *
	 * @param prod the prod
	 * @throws SQLException the SQL exception
	 */
	public void productInsert(Product prod) throws SQLException 
	{
		String sql = "INSERT INTO products(item, description, price, type_item) VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps;
		ps = connection.prepareStatement(sql);
		ps.setString(1, prod.getProdName());
		ps.setString(2, prod.getProdDescription());
		ps.setDouble(3, prod.getProdPrice());
		ps.setString(4, prod.getProdType());
		ps.execute();
		
		connection.commit();
	}
	
	
	/**
	 * Montador de produto.
	 *
	 * @param rs the rs
	 * @return dados do produto
	 * @throws SQLException the SQL exception
	 */
	private Product productAssembler(ResultSet rs) throws SQLException 
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		
		Product assembler = new Product();		
		Double value = (rs.getDouble(("price")));

		assembler.setProdName(rs.getString("item"));
		assembler.setProdDescription(rs.getString("description"));
		assembler.setProdPrice(rs.getDouble(("price")));
		assembler.setProdPriceFormatter(formatter.format(value));
		assembler.setProdType(rs.getString("type_item"));
		assembler.setProdCode(rs.getInt("code"));
 
		return assembler;
	}
}
