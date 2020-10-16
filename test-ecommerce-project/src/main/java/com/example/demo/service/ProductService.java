package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CartDetails;
import com.example.demo.domain.CartItemDetails;
import com.example.demo.domain.EmailRequest;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.google.gson.Gson;

import io.netty.handler.codec.json.JsonObjectDecoder;

@Service
public class ProductService {
	
	@Value("${environment:local}_EMAIL_Q")
    private String emailQ;
	
	@Autowired
	RabbitTemplate rmqTemplate;

	public String getProductDetails(String productName, String sortingKey) {
		List<Product> productList = new ArrayList<Product>();
		if(productName != null) {
			productList = Product.getProductByName(productName);
		} else {
			productList = Product.findAll();
		}
		
		if(sortingKey != null ) {
			if(sortingKey.equalsIgnoreCase("ByName")) {
				Collections.sort(productList, new SortByName());
			} else if(sortingKey.equalsIgnoreCase("ByPriceInAscending")) {
				Collections.sort(productList, new SortByPriceAscending());
			} else if(sortingKey.equalsIgnoreCase("ByPriceInDecending")) {
				Collections.sort(productList, new SortByPriceDecending());
			} else if(sortingKey.equalsIgnoreCase("ByNameAndPrice")) {
				Collections.sort(productList, new SortByNameAndPrice());
			}
		}
		
		return productList.toString();
	}

	
	class SortByName implements Comparator<Product> {

	    @Override
	    public int compare(Product p1, Product p2) {
	    	return p1.getProductName().compareTo(p2.getProductName());
	    }

	}
	
	class SortByPriceAscending implements Comparator<Product> {

	    @Override
	    public int compare(Product p1, Product p2) {
	    	return p1.getPrice().compareTo(p2.getPrice());
	    }

	}
	
	class SortByPriceDecending implements Comparator<Product> {

	    @Override
	    public int compare(Product p1, Product p2) {
	    	return -p1.getPrice().compareTo(p2.getPrice());
	    }

	}
	
	class SortByNameAndPrice implements Comparator<Product> {

	    @Override
	    public int compare(Product p1, Product p2) {
	    	int res;
	    	res =  p1.getProductName().compareTo(p2.getProductName());
	    	if (res == 0)
	    		res = -p1.getPrice().compareTo(p2.getPrice());
	    	return res;
	    }

	}

	public String getCartItemDetails(List<CartDetails> cartdetails) {
		Map<String, Integer> quantityDetails = new HashMap<String, Integer>();
		List<String> productCode = new ArrayList<String>();
		List<CartItemDetails> cartList = new ArrayList<CartItemDetails>();
		if(cartdetails != null) {
			for(CartDetails details :cartdetails) {
				productCode.add(details.getProductCode());
				quantityDetails.put(details.getProductCode(), details.getQuantity());
			}
			List<Product> productList = Product.getProductByCode(productCode);
			if(productList != null) {
				for(Product p :productList) {
					Double totalPrice = quantityDetails.get(p.getProductCode()) * p.getPrice();
					cartList.add(new CartItemDetails(p.getProductCode(), p.getProductName(), quantityDetails.get(p.getProductCode()),
							p.getPrice(), totalPrice));
				}
			}
			return cartList.toString();
		}
		return "Cart Is Empty";
	}

	public String proceedCheckOut(List<CartItemDetails> carts, String userId) {
		Double totalPrice = 0.0;
		for(CartItemDetails c: carts) {
			totalPrice = totalPrice+ c.getTotalPrice();
		}
		EmailRequest req = new EmailRequest();
		req.setTotalAmount(totalPrice);
		req.setUser(User.findById(userId));
		String emailReqJson = new Gson().toJson(req, EmailRequest.class);
		rmqTemplate.convertAndSend(emailQ, emailReqJson);
		return null;
	}
}
