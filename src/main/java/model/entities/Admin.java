package model.entities;

import java.util.Objects;

public class Admin {

	private String adminName;
	private String phone;
	private String email;
	private String login;
	private String password;
	private String partner;

	public Admin() {

	}

	public Admin(String adminName, String phone, String email, String login, String password, String partner) {
		this.adminName = adminName;
		this.phone = phone;
		this.email = email;
		this.login = login;
		this.password = password;
		this.partner = partner;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(login);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
		return Objects.equals(login, other.login);
	}

}
