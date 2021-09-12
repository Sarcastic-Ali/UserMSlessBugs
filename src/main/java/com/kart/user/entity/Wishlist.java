package com.kart.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Wishlist {

	@Id
	@Column(nullable = false)
	String buyerid;
	//@Id
	@Column(nullable = false)
	String prodid;
	
	public Wishlist() {
		super();
	}

	public String getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public Wishlist(String buyerid, String prodid) {
		super();
		this.buyerid = buyerid;
		this.prodid = prodid;
		
	}

	@Override
	public String toString() {
		return "Wishlist [buyerid=" + buyerid + ", prodid=" + prodid + "]";
	}
}
