package model.entities;

public class Product {

//	private Long prodCod;
	private String prodName;
	private String prodDescription;
	private Double prodPrice;
	private String prodPriceFormatter;
	private String prodType;

	public Product() {

	}

	public Product(String prodName, String prodDescription, Double prodPrice, String prodPriceFormatter,
			String prodType) {
		this.prodName = prodName;
		this.prodDescription = prodDescription;
		this.prodPrice = prodPrice;
		this.prodPriceFormatter = prodPriceFormatter;
		this.prodType = prodType;
	}

	public Product(String prodName, String prodDescription, Double prodPrice, String prodType) {
		this.prodName = prodName;
		this.prodDescription = prodDescription;
		this.prodPrice = prodPrice;
		this.prodType = prodType;
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
