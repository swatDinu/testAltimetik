package com.example.demo.domain;

import java.io.Serializable;
import java.util.List;

public class EmailRequest  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<CartItemDetails> cartItemDetails;
	
	private User user;
	
	private Double totalAmount;
	
	public Double getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<CartItemDetails> getCartItemDetails() {
		return cartItemDetails;
	}
	
	public void setCartItemDetails(List<CartItemDetails> cartItemDetails) {
		this.cartItemDetails = cartItemDetails;
	}
	

}
