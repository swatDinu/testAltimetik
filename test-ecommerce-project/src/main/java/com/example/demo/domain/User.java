package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User extends BaseEntity implements Serializable{

	public static User findById(String userId) {
		try {
			return entityManager().createQuery("select o from User o where o.userId = ?1", User.class)
					.setParameter(1, userId).getSingleResult();
		} catch (Exception e) {
			logger.info("Exception while finding All ", e);
			return null;
		}
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserId")
    private Integer userId;
	
	@Column(name = "UserName")
    private String userName;
	
	@Column(name = "FirstName")
    private String firstName;
	
	@Column(name = "MiddleName")
    private String middleName;
	
	@Column(name = "LastName")
    private String lastName;
	
	@Column(name = "Password")
    private String password;

	@Column(name = "Email")
    private String email;
	
	@Column(name = "PhoneNumber")
    private String phoneNumber;
	
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
	
	public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
	
	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
	
	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
	public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
	public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
	
	public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
