package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {

	private Long orderCode;
	private String comments;
	private Client orderClient;

	private List<Product> products = new ArrayList<>();

	public Order() {

	}

	public Order(Long orderCode, String comments, Client orderClient) {
		super();
		this.orderCode = orderCode;
		this.comments = comments;
		this.orderClient = orderClient;
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

	public double getTotal() {
		Double total = 0.0;
		for (Product p : products) {
			total += p.getProdPrice();
		}
		return total;
	}
}
