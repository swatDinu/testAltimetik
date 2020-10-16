package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product extends BaseEntity implements Serializable {
	
	public static final String FIND_By_PRODUCTNAME = "select o from Product o where o.productName like (%?%) o orderBy o.price";
	public static final String FIND_ALL = "select o from Product o orderBy o.price";
	public static final String FIND_By_PRODUCTCODES = "select o from Product o where o.productCode in (?1)";

	public static List<Product> findAll() {
		try {
			return entityManager().createQuery(FIND_ALL, Product.class).getResultList();
		} catch (Exception e) {
			logger.info("Exception while finding All ", e);
			return null;
		}
    }

	public static List<Product> getProductByName(String productName) {
		try {
			return entityManager().createQuery(FIND_By_PRODUCTNAME, Product.class)
                .setParameter(1, productName).getResultList();
		} catch (Exception e) {
			logger.info("Exception while finding by ProductName", e);
			return null;
		}
    }
	
	public static List<Product> getProductByCode(List<String> productCode) {
		try {
			return entityManager().createQuery(FIND_By_PRODUCTCODES, Product.class)
                .setParameter(1, productCode).getResultList();
		} catch (Exception e) {
			logger.info("Exception while finding by ProductName", e);
			return null;
		}
	}
		
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product p = (Product) obj;
		return productCode.equals(p.productCode);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ProductId")
    private Integer productId;
	
	@Column(name = "ProductCode", nullable = false, length = 15)
    private String productCode;
	
	@Column(name = "ProductName", nullable = false, length = 25)
    private String productName;
	
	@Column(name = "Price")
    private Double price;

	@Column(name = "ProductDetails", length = 100)
    private String productDeatils;
	
	@Column(name="CreatedDttm")
	private Date createdDttm;
	
	@Column(name="UpdatedDttm")
	private Date updatedDttm;
	
	public Date getUpdatedDttm() {
		return updatedDttm;
	}
	
	public void serUpdatedDttm(Date updatedDttm) {
		this.updatedDttm = updatedDttm;
	}
	
	public Date getCreatedDttm() {
		return createdDttm;
	}
	
	public void serCreatedDttm(Date createdDttm) {
		this.createdDttm = createdDttm;
	}
	
	public String getProductDetails() {
        return productDeatils;
    }

    public void setProductDetails(String productDeatils) {
        this.productDeatils = productDeatils;
    }
    
	public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
    
	public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
