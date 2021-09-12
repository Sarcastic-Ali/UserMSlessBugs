package com.kart.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Seller {
	
	@Id
	@GenericGenerator(name = "seller_id", strategy = "com.kart.user.generator.SellerIdGenerator")
    @GeneratedValue(generator = "seller_id")
	String sellerid;
	
	@Column(nullable = false)
	String name;
	
	@Column(nullable = false)
	String email;
	
	@Column(nullable = false)
	String phoneno;
	
	@Column(nullable = false)
	String password;
	
	@Column(nullable = false)
	Boolean isactive;
	
	public String getSellerid() {
		return sellerid;
	}
	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getIsactive() {
		return isactive;
	}
	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	
	public Seller(String sellerid, String name, String email, String phoneno, String password, Boolean isactive) {
		super();
		this.sellerid = sellerid;
		this.name = name;
		this.email = email;
		this.phoneno = phoneno;
		this.password = password;
		this.isactive = isactive;
	}
	public Seller() {
		super();
		
	}
	@Override
	public String toString() {
		return "Seller [sellerid=" + sellerid + ", name=" + name + ", email=" + email + ", phoneno=" + phoneno
				+ ", password=" + password + ", isactive=" + isactive + "]";
	}
	
	
}
