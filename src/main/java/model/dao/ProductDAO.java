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
	
	public List<Product> productSearch(String prodType) 
	{
		List<Product> itemFound = new ArrayList<>();
		try 
		{
			String sql = null;
			if(prodType.equals("Pizza") || prodType == "Pizza") {
				sql = "SELECT * FROM products WHERE type_item = 'Pizza'";
			}
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs 	     = ps.executeQuery();

			while (rs.next()) {
				itemFound.add(productAssembler(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemFound;
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
		
 
		return assembler;
	}
}
