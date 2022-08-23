package model.entities;

public class Client {

	private String name;
	private String phone;
	private String email;
	private String adress;
	private String reference;

	public Client() {

	}

	public Client(String name, String phone, String email, String adress, String reference) {
		this.name      = name;
		this.phone     = phone;
		this.email     = email;
		this.adress    = adress;
		this.reference = reference;
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

	@Override
	public String toString() {
		return "Client [name=" + name + ", phone=" + phone + ", email=" + email + ", adress=" + adress + ", reference="
				+ reference + "]";
	}

	
}
