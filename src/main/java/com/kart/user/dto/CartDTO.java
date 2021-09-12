package com.kart.user.dto;

import com.kart.user.entity.Cart;

public class CartDTO {

	String buyerid;
	String prodid;
	Integer quantity;
	
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "CartDTO [buyerid=" + buyerid + ", prodid=" + prodid + ", quantity=" + quantity + "]";
	}
	
	public Cart createEntity() {
		Cart cart = new Cart();
		cart.setBuyerid(this.buyerid);
		cart.setProdid(this.prodid);
		cart.setQuantity(this.quantity);
		return cart;
	}
	public static CartDTO valueOf(Cart c) {
		CartDTO cdto = new CartDTO();
		cdto.setBuyerid(c.getBuyerid());
		cdto.setProdid(c.getProdid());
		cdto.setQuantity(c.getQuantity());
		return cdto;
	}
	
}
