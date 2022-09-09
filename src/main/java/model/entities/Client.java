package model.entities;

public class Client {

	private String name;
	private String phone;
	private String email;
	private String adress;
	private String reference;
	private Order order;
	private Long id;

	public Client() {

	}

	public Client(String name, String phone, String email, String adress, String reference) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.adress = adress;
		this.reference = reference;
	}
	
	public String getPhoneWhats() {
		return phone.replace("-", "");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
