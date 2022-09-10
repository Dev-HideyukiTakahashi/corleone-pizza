package model.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order implements Comparable<Order> {

	private Long orderCode;
	private LocalDateTime date;
	private String dateString;
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

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDate() {
		if(date == null) {
			return dateString;
		}
		else {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			return dateFormat.format(date);
		}

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
