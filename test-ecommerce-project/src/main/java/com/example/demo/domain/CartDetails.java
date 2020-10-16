package com.example.demo.domain;

import java.io.Serializable;

public class CartDetails  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String productCode;
	
	private Integer quantity;
	
	public CartDetails(String productCode, Integer quantity) {
		this.productCode = productCode;
		this.quantity = quantity;
	}
	
	
	public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
	public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
}
