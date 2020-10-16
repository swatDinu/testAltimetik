package com.example.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.demo.domain.CartDetails;

@SpringBootApplication
public class TestEcommerceProjectApplication {

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}
	
	@Bean
    public RedisTemplate<String,List<CartDetails>> redisTemplate() {
        RedisTemplate<String, List<CartDetails>> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(TestEcommerceProjectApplication.class, args);
	}

}
