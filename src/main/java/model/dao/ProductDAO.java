package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import config.DatabaseConnection;
import model.entities.Product;

public class ProductDAO {

	private Connection connection;

	public ProductDAO() {
		connection = DatabaseConnection.getConnection();
	}
	
	public List<Product> productSearch(String prodType) throws SQLException 
	{
		List<Product> itemFound = new ArrayList<>();
		String sql = null;
		if(prodType.equals("Pizza") || prodType == "Pizza") {
			sql = "SELECT * FROM products WHERE type_item = 'Pizza'";
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
		ps.setString(1, prodCode);
		ResultSet rs 	     = ps.executeQuery();

		while (rs.next()) {
			item = (productAssembler(rs));
		}

		return item;
	}
	
	public void productUpdate(String code, String description) throws SQLException 
	{
		Product item = new Product();
		String sql   = "UPDATE products	SET description=? WHERE code=?";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, description);
		ps.setString(2, code);
		ps.executeUpdate();
		
		connection.commit();
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
		assembler.setProdCode(rs.getString("code"));
 
		return assembler;
	}
}
