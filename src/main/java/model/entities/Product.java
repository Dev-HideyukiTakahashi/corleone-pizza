package model.entities;

public class Product {

	private String prodName;
	private String prodDescription;
	private Double prodPrice;
	private String prodPriceFormatter; // Formatador de vírgula para exibir na tela
	private String prodType;
	private String prodCode;

	public Product() {

	}

	public Product(String prodName, String prodDescription, Double prodPrice, String prodType) {
		this.prodName = prodName;
		this.prodDescription = prodDescription;
		this.prodPrice = prodPrice;
		this.prodType = prodType;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdDescription() {
		return prodDescription;
	}

	public void setProdDescription(String prodDescription) {
		this.prodDescription = prodDescription;
	}

	public Double getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Double prodPrice) {
		this.prodPrice = prodPrice;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public String getProdPriceFormatter() {
		return prodPriceFormatter;
	}

	public void setProdPriceFormatter(String prodPriceFormatter) {
		this.prodPriceFormatter = prodPriceFormatter;
	}

}
