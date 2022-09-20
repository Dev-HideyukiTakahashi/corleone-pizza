package model.entities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Order implements Comparable<Order> {

	private Long orderCode;
	private Timestamp date;
	private String comments;
	private Client orderClient;
	private Motoboy orderMotoboy;

	private List<Product> products = new ArrayList<>();

	public Order() {

	}

	public Order(Long orderCode, String comments, Client orderClient) {
		super();
		this.orderCode = orderCode;
		this.comments = comments;
		this.orderClient = orderClient;
	}

	public Motoboy getOrderMotoboy() {
		return orderMotoboy;
	}

	public void setOrderMotoboy(Motoboy orderMotoboy) {
		this.orderMotoboy = orderMotoboy;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getDate() {
		if (date != null) {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date.getTime());
		}
		return null;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Long orderCode) {
		this.orderCode = orderCode;
	}

	public Client getOrderClient() {
		return orderClient;
	}

	public void setOrderClient(Client orderClient) {
		this.orderClient = orderClient;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void addProduct(Product product) {
		products.add(product);
	}

	public Integer getProductItem() {
		return products.get(0).getProdCode();
	}

	public void removeProduct(Product product) {
		products.remove(product);
	}

	public String getTotal() {
		Double total = 0.0;
		for (Product p : products) {
			total += p.getProdPrice();
		}
		return String.format("%.2f", total);
	}

	@Override
	public int compareTo(Order o) {
		return orderCode.compareTo(o.getOrderCode());
	}
}
