package com.example.demo.domain;

import java.io.Serializable;

public class CartItemDetails  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String productCode;
	
	private String productName;
	
	private Integer quantity;
	
	private Double price;
	
	private Double totalPrice;
	
	public CartItemDetails(String productCode, String productName, Integer quantity, Double price, Double totalPrice) {
		this.productCode = productCode;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
	}
	
	public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
	
	public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
	public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
}
