package com.example.demo.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.CartDetails;
import com.example.demo.domain.CartItemDetails;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;

@Controller
@RequestMapping("/test")
public class TestECommerceController {

	
	@Autowired
	ProductService productService;
	
	@Autowired
	CartService cartService;
	
	static Log logger = LogFactory.getLog(TestECommerceController.class);
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.GET)
    public @ResponseBody String getProducts(@RequestParam("productName") String productName, 
    		@RequestParam(value = "sortingKey", required = false) String sortingKey) {
		return productService.getProductDetails(productName, sortingKey);
    }
	
	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    public @ResponseBody String addToCart(@RequestParam("productCode") String productCode, 
    		@RequestParam(value = "userId") String userId, @RequestParam(value = "quantity") Integer quantity) {
		cartService.addToCart(productCode, userId, quantity);
		return "SUCCESS";
    }
	
	@RequestMapping(value = "/removeFromCart/{productCode}", method = RequestMethod.POST)
    public @ResponseBody String removeFromCart(@PathVariable("productCode") String productCode,
    		@RequestParam(value = "userId") String userId) {
		return cartService.removeFromCart(productCode, userId);
    }
	
	@RequestMapping(value = "/viewCart/{userId}", method = RequestMethod.POST)
    public @ResponseBody String viewCart(@PathVariable("userId") String userId) {
		List<CartDetails> cartdetails = cartService.viewCart(userId);
		return productService.getCartItemDetails(cartdetails);
    }
	
	@RequestMapping(value = "/proceedCheckout", method = RequestMethod.POST)
    public @ResponseBody String proceedCheckOut(@RequestParam List<CartItemDetails> carts, 
    		@RequestParam("userId") String userId) {
		return productService.proceedCheckOut(carts, userId);
    }
}
