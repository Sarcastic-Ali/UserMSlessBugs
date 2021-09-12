package com.kart.user.dto;

import com.kart.user.entity.Wishlist;

public class WishlistDTO {

	String buyerid;
	String prodid;
	
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
	
	@Override
	public String toString() {
		return "WishlistDTO [buyerid=" + buyerid + ", prodid=" + prodid + "]";
	}

	public Wishlist createEntity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	}

