package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.DatabaseConnection;
import model.entities.Client;
import model.entities.Order;
import model.entities.Product;

public class OrderDAO {
	
	private Connection connection;
	

	public OrderDAO() {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}

	
	public void insert(String comments, Client client, Order order) throws SQLException {
		
		
			
			String sql = "INSERT INTO tb_order(comments, order_client, product_id) VALUES (?, ?, ?)";
			
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, comments);
			ps.setLong(2, client.getId());
			ps.setLong(3, order.getProductItem());
			ps.executeUpdate();
			connection.commit();
			
			ResultSet rs = ps.getGeneratedKeys();
			Long lastCode = null;
			
			if(rs.next()) {
				lastCode = rs.getLong("order_code");
			}
			
			{
				for(int i = 1; i < order.getProducts().size(); i++) 
				{
					
					sql = "INSERT INTO tb_order(order_code, comments, order_client, product_id) VALUES (?, ?, ?, ?)";
					
					ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					
					ps.setLong(1, lastCode);
					ps.setString(2, comments);
					ps.setLong(3, client.getId());
					ps.setLong(4, order.getProducts().get(i).getProdCode());
					ps.executeUpdate();
					
					connection.commit();
				}
			}
		
	}
}
