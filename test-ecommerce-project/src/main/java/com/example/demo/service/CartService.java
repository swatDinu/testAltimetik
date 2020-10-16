package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.mapping.Array;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CartDetails;

@Service
public class CartService {
	
	private RedisTemplate<String, List<CartDetails>> redisTemplate;
	
	@SuppressWarnings("rawtypes")
	private HashOperations hashOperations = redisTemplate.opsForHash();

	@SuppressWarnings("unchecked")
	public void addToCart(String productCode, String userId, Integer quantity) {
		List<CartDetails> cartDetails = (List<CartDetails>) hashOperations.get("CARTITEMS", userId);
		if(cartDetails == null) {
			cartDetails = new ArrayList<CartDetails>();
		} 
		cartDetails.add(new CartDetails(productCode, quantity));
		hashOperations.put("CARTITEMS", userId, cartDetails);
	}

	@SuppressWarnings("unchecked")
	public String removeFromCart(String productCode, String userId) {
		List<CartDetails> cartDetails = (List<CartDetails>) hashOperations.get("CARTITEMS", userId);
		cartDetails.remove(productCode);
		hashOperations.put("CARTITEMS", userId, cartDetails);
		return "SUCCESS";
	}

	public List<CartDetails> viewCart(String userId) {
		return (List<CartDetails>) hashOperations.get("CARTITEMS", userId);
	}

}
