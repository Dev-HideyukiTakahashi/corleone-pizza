package model.entities;

public class Motoboy {

	private Long   motoboyId;
	private String motoboyName;
	private String motoboyPhone;
	private String motoboyAdress;

	public Motoboy() {

	}

	public Motoboy(String motoboyName, String motoboyPhone, String motoboyAdress) {
		this.motoboyName   = motoboyName;
		this.motoboyPhone  = motoboyPhone;
		this.motoboyAdress = motoboyAdress;
	}

	public Long getMotoboyId() {
		return motoboyId;
	}

	public void setMotoboyId(Long motoboyId) {
		this.motoboyId = motoboyId;
	}

	public String getMotoboyName() {
		return motoboyName;
	}

	public void setMotoboyName(String motoboyName) {
		this.motoboyName = motoboyName;
	}

	public String getMotoboyPhone() {
		return motoboyPhone;
	}

	public void setMotoboyPhone(String motoboyPhone) {
		this.motoboyPhone = motoboyPhone;
	}

	public String getMotoboyAdress() {
		return motoboyAdress;
	}

	public void setMotoboyAdress(String motoboyAdress) {
		this.motoboyAdress = motoboyAdress;
	}

}
