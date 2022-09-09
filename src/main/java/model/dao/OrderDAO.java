package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
			
			String sql = "INSERT INTO tb_order(comments, order_client, product_id, order_data) VALUES (?, ?, ?, ?)";
			
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, comments);
			ps.setLong(2, client.getId());
			ps.setLong(3, order.getProductItem());
			ps.setString(4, order.getDate());
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
					
					sql = "INSERT INTO tb_order(order_code, comments, order_client, product_id, order_data) VALUES (?, ?, ?, ?, ?)";
					
					ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					
					ps.setLong(1, lastCode);
					ps.setString(2, comments);
					ps.setLong(3, client.getId());
					ps.setLong(4, order.getProducts().get(i).getProdCode());
					ps.setString(5, order.getDate());
					ps.executeUpdate();
					
					connection.commit();
				}
			}
	}
	
	
	public List<Order> findAll() throws SQLException
	{
		List<Order> list = new ArrayList<>();
		
		String sql = "SELECT * "
					+ "FROM tb_order "
					+ "INNER JOIN client "
					+ "ON (tb_order.order_client = client.id) "
					+ "INNER JOIN products "
					+ "ON (tb_order.product_id = products.code) ORDER BY order_code";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs		 = ps.executeQuery();
		
		while(rs.next())
		{
			Long orderCode = rs.getLong("order_code");			
			int lastIdx = list.size() - 1;
			
			// Se não tiver elementos salva o primeiro
			if(list.size() < 1) 
			{
				Order order = new Order();
				
				order.setComments(rs.getString("comments"));
				order.setOrderCode(rs.getLong("order_code"));
				order.setDateString((rs.getString("order_data")));
				
				Client client = new Client();
				client.setId(rs.getLong("id"));
				client.setName(rs.getString("name"));
				client.setPhone(rs.getString("phone"));
				client.setAdress(rs.getString("adress"));
				client.setReference(rs.getString("reference"));
				
				order.setOrderClient(client);
				
				Product product = new Product();
				product.setProdCode(rs.getInt("code"));
				product.setProdName(rs.getString("item"));
				product.setProdDescription(rs.getString("description"));
				product.setProdPrice(rs.getDouble("price"));
				
				order.getProducts().add(product);
				
				list.add(order);
				lastIdx = list.size() - 1;
				
			}
			else if(list.size() > 1 && list.get(lastIdx).getOrderCode() == orderCode) // se tiver elemento com mesmo código de pedido
			{
				Product product = new Product();
				product.setProdCode(rs.getInt("code"));
				product.setProdName(rs.getString("item"));
				product.setProdDescription(rs.getString("description"));
				product.setProdPrice(rs.getDouble("price"));
				
				list.get(lastIdx).getProducts().add(product);
			}
			else if(list.get(lastIdx).getOrderCode() != orderCode)
			{
				Order order = new Order();
				
				order.setComments(rs.getString("comments"));
				order.setOrderCode(rs.getLong("order_code"));
				order.setDateString((rs.getString("order_data")));
				
				Client client = new Client();
				client.setId(rs.getLong("id"));
				client.setName(rs.getString("name"));
				client.setPhone(rs.getString("phone"));
				client.setAdress(rs.getString("adress"));
				client.setReference(rs.getString("reference"));
				
				order.setOrderClient(client);
				
				Product product = new Product();
				product.setProdCode(rs.getInt("code"));
				product.setProdName(rs.getString("item"));
				product.setProdDescription(rs.getString("description"));
				product.setProdPrice(rs.getDouble("price"));
				
				order.getProducts().add(product);
				
				list.add(order);
			}
			
		}
		
		return list;
		
	}
	
	
	
}
