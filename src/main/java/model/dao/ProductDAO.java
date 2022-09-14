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
import model.entities.Admin;
import model.entities.Product;

public class ProductDAO {

	private Connection connection;

	public ProductDAO() {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}
	
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
	
	public void productUpdate(String code, String value, String option, Admin user) 
	{
		try 
		{
			String sql   = null;
			if(option.equals("updatePizza") || option == "updatePizza") {
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
			
			// Se algum usu�rio diferente do admin alterar dados, registro no log
			if(user != null && user.getId() != 1) 
			{
				DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				
				LocalDateTime date = LocalDateTime.now();
				String log = "Usuário '"+user.getAdminName()+"', alterou dados do produto:\nCódigo: "
						+ code +"\nNome alterado: '" + value + "'";
				
				sql = "INSERT INTO tb_log(date, field)	VALUES (?, ?)";
				ps = connection.prepareStatement(sql);
				ps.setString(1, dateFormat.format(date));
				ps.setString(2, log);
				ps.execute();
				connection.commit();
		    }
		}
		catch(SQLException e) {
			try {
				connection.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public void productInsert(Product prod) 
	{
		
		try 
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
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}
	
	
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
