package model.entities;

import java.util.Objects;

public class Product implements Comparable<Product> {

	private String prodName;
	private String prodDescription;
	private Double prodPrice;
	private String prodPriceFormatter; // Formatador de virgula para exibir na tela
	private String prodType;
	private Integer prodCode;

	public Product() {

	}

	public Product(String prodName, String prodDescription, Double prodPrice, String prodType) {
		this.prodName = prodName;
		this.prodDescription = prodDescription;
		this.prodPrice = prodPrice;
		this.prodType = prodType;
	}

	public Integer getProdCode() {
		return prodCode;
	}

	public void setProdCode(Integer prodCode) {
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

	@Override
	public int compareTo(Product o) {
		return this.getProdCode().compareTo(o.getProdCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(prodCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(prodCode, other.prodCode);
	}

}
