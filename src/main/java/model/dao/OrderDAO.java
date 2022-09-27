package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.entities.Client;
import model.entities.Motoboy;
import model.entities.Order;
import model.entities.Product;
import model.entities.User;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderDAO.
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
public class OrderDAO {
	
	/** The connection. */
	private Connection connection;

	/**
	 * Instancia nova conexao com banco de dados.
	 */
	public OrderDAO() {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}

	/**
	 * Registra um novo pedido no banco de dados.
	 * A data eh gerada automaticamente pelo postgresql (timestamp).
	 * 	 
	 *
	 * @param comments observacoes do pedido
	 * @param client dados do cliente
	 * @param order dados do pedido
	 * @param motoboyId id do motoboy que entrega
	 * @param user the user
	 * @throws SQLException the SQL exception
	 */
	public void insert(String comments, Client client, Order order, Long motoboyId, User user) throws SQLException 
	{
		String sql = "INSERT INTO tb_order(comments, order_client, product_id, order_motoboy) VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		ps.setString(1, comments);
		ps.setLong(2, client.getId());
		ps.setLong(3, order.getProductItem());
		ps.setLong(4, motoboyId);
		ps.executeUpdate();
		connection.commit();
		
		ResultSet rs = ps.getGeneratedKeys();
		Long lastCode = null;
		if(rs.next()){lastCode = rs.getLong("order_code");}
		
		for(int i = 1; i < order.getProducts().size(); i++) 
		{
			sql = "INSERT INTO tb_order(order_code, comments, order_client, product_id, order_motoboy) VALUES (?, ?, ?, ?, ?)";
			
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setLong(1, lastCode);
			ps.setString(2, comments);
			ps.setLong(3, client.getId());
			ps.setLong(4, order.getProducts().get(i).getProdCode());
			ps.setLong(5, motoboyId);
			ps.executeUpdate();
			
			connection.commit();
		}
		
		if(ps.getGeneratedKeys() != null){
			
			LocalDateTime date = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
			String log = "Usuário '"+user.getUserName()+"' finalizou um pedido.\nCódigo: "
						+ lastCode +"\nCliente: " + client.getName() + "\nValor: " + order.getTotal();
			
			sql = "INSERT INTO tb_log(date, field)	VALUES (?, ?)";
			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, Timestamp.valueOf(date));
			ps.setString(2, log);
			ps.execute();
			connection.commit();
		}
	}
	
	/**
	 * Lista todos os pedidos registrados no banco de dados, ordenados por codigo.
	 *
	 * @return lista de pedidos
	 * @throws SQLException the SQL exception
	 */
	public List<Order> findAll() throws SQLException
	{
		List<Order> list = new ArrayList<>();
		
		String sql = "SELECT * "
					+ "FROM tb_order "
					+ "INNER JOIN client "
					+ "ON (tb_order.order_client = client.id) "
					+ "INNER JOIN motoboy "
					+ "ON (tb_order.order_motoboy = motoboy.motoboy_id) "
					+ "INNER JOIN products "
					+ "ON (tb_order.product_id = products.code) ORDER BY order_code";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs		 = ps.executeQuery();
		
		while(rs.next())
		{
			Long orderCode = rs.getLong("order_code");			
			int lastIdx = list.size() - 1;
			
			if(list.size() < 1)	{
				Order order = orderAssembler(rs);
				
				list.add(order);
				lastIdx = list.size() - 1;
			}
			else if(list.get(lastIdx).getOrderCode() == orderCode) 
			{
				Product product = new Product();
				product.setProdCode(rs.getInt("code"));
				product.setProdName(rs.getString("item"));
				product.setProdDescription(rs.getString("description"));
				product.setProdPrice(rs.getDouble("price"));
				
				list.get(lastIdx).getProducts().add(product);
			}
			else if(list.get(lastIdx).getOrderCode() != orderCode){
				Order order = orderAssembler(rs);
				list.add(order);
			}
		}
		return list;
	}
	
	/**
	 * Lista todos os pedidos registrados no banco de dados, ordenados por codigo.
	 *
	 * @return lista de pedidos
	 * @throws SQLException the SQL exception
	 */
	public List<Order> findAllPage() throws SQLException
	{
		List<Order> list = new ArrayList<>();
		
		String sql = "SELECT * "
					+ "FROM tb_order "
					+ "INNER JOIN client "
					+ "ON (tb_order.order_client = client.id) "
					+ "INNER JOIN motoboy "
					+ "ON (tb_order.order_motoboy = motoboy.motoboy_id) "
					+ "INNER JOIN products "
					+ "ON (tb_order.product_id = products.code) ORDER BY order_code";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs		 = ps.executeQuery();
		
		while(rs.next())
		{
			Long orderCode = rs.getLong("order_code");			
			int lastIdx = list.size() - 1;
			
			if(list.size() < 1)	{
				Order order = orderAssembler(rs);
				
				list.add(order);
				lastIdx = list.size() - 1;
			}
			else if(list.get(lastIdx).getOrderCode() == orderCode) 
			{
				Product product = new Product();
				product.setProdCode(rs.getInt("code"));
				product.setProdName(rs.getString("item"));
				product.setProdDescription(rs.getString("description"));
				product.setProdPrice(rs.getDouble("price"));
				
				list.get(lastIdx).getProducts().add(product);
			}
			else if(list.get(lastIdx).getOrderCode() != orderCode){
				Order order = orderAssembler(rs);
				list.add(order);
			}
		}
		return list;
	}
	
	
	/**
	 * Lista todos os pedidos registrados no banco de dados, ordenados por codigo.
	 * Filtra pelas data de inicio e fim.
	 * Utilizado para gerar o relatorio com os elementos.
	 *
	 * @param dateBegin data de inicio
	 * @param dateFinal data final
	 * @return the list
	 * @throws SQLException the SQL exception
	 */
	public List<Order> findAllByDate(String dateBegin, String dateFinal) throws SQLException 
	{
		List<Order> list = new ArrayList<>();
		
		String sql = "SELECT * "
					+ "FROM tb_order "
					+ "INNER JOIN client "
					+ "ON (tb_order.order_client = client.id) "
					+ "INNER JOIN motoboy "
					+ "ON (tb_order.order_motoboy = motoboy.motoboy_id) "
					+ "INNER JOIN products "
					+ "ON (tb_order.product_id = products.code) "
					+ "WHERE order_data BETWEEN ? AND ? "
					+ "ORDER BY order_code";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setTimestamp(1, Timestamp.valueOf(dateBegin));
		ps.setTimestamp(2, Timestamp.valueOf(dateFinal));
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			Long orderCode = rs.getLong("order_code");			
			int lastIdx = list.size() - 1;
			
			if(list.size() < 1)	{
				Order order = orderAssembler(rs);
				
				list.add(order);
				lastIdx = list.size() - 1;
			}
			else if(list.get(lastIdx).getOrderCode() == orderCode) 
			{
				Product product = new Product();
				product.setProdCode(rs.getInt("code"));
				product.setProdName(rs.getString("item"));
				product.setProdDescription(rs.getString("description"));
				product.setProdPrice(rs.getDouble("price"));
				
				list.get(lastIdx).getProducts().add(product);
			}
			else if(list.get(lastIdx).getOrderCode() != orderCode){
				Order order = orderAssembler(rs);
				list.add(order);
			}
		}
		return list;
	}
	
	/**
	 * Calcula quantas paginas vao ser exibidas de acordo com o numero de registros.
	 * A view de "Pedidos", mostra 10 registros por pagina.
	 *
	 * @return numero de paginas na view
	 * @throws SQLException the SQL exception
	 */
	public Integer totalPages() throws SQLException 
	{
		String 			  sql  = "SELECT COUNT(DISTINCT order_code) as total FROM tb_order LIMIT 10 OFFSET 0";
		PreparedStatement ps   = connection.prepareStatement(sql);
		ResultSet		  rs   = ps.executeQuery();
		
		Double result = 0.0;
		while(rs.next()) {result = rs.getDouble("total");}

		Double offset = result / 10;
		offset 		  = offset % 2 > 0 ? offset + 1 : offset;
		
		return offset.intValue();
	}

	
	/**
	 * Busca um pedido no banco de dados pelo codigo.
	 * Acompanha todos os dados dos relacionamentos.
	 *
	 * @param code codigo do pedido
	 * @return dados do pedido
	 * @throws SQLException the SQL exception
	 */
	public Order findByCode(Long code) throws SQLException 
	{
		String sql = "SELECT * "
				+ "FROM tb_order "
				+ "INNER JOIN client "
				+ "ON (tb_order.order_client = client.id) "
				+ "INNER JOIN motoboy "
				+ "ON (tb_order.order_motoboy = motoboy.motoboy_id) "
				+ "INNER JOIN products "
				+ "ON (tb_order.product_id = products.code) "
				+ "WHERE tb_order.order_code = ? ORDER BY order_code";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, code);
		
		ResultSet rs = ps.executeQuery();
		
		Order order = null;
		
		while(rs.next()) 
		{
			if(order == null) {
				order = orderAssembler(rs);
			}
			else {
				Product product = new Product();
				product.setProdCode(rs.getInt("code"));
				product.setProdName(rs.getString("item"));
				product.setProdDescription(rs.getString("description"));
				product.setProdPrice(rs.getDouble("price"));
				
				order.getProducts().add(product);
			}
			
		}
			
		return order;
	}
	
	/**
	 * Montador de pedidos.
	 *
	 * @param rs the rs
	 * @return pedido com dados
	 * @throws SQLException the SQL exception
	 */
	private Order orderAssembler(ResultSet rs) throws SQLException 
	{
		Order order = new Order();
		
		order.setComments(rs.getString("comments"));
		order.setOrderCode(rs.getLong("order_code"));
		order.setDate((rs.getTimestamp("order_data")));
		
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
		
		order.setOrderMotoboy(new Motoboy());
		order.getOrderMotoboy().setMotoboyName(rs.getString("motoboy_name"));
		
		return order;
	}


}
