package com.kart.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Buyer {


	@Id
	@GenericGenerator(name = "buyer_id", strategy = "com.kart.user.generator.BuyerIdGenerator")
    @GeneratedValue(generator = "buyer_id")  
	String buyerid;
	
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
	
	@Column(nullable = false)
	Boolean isprivileged;
	
	@Column(nullable = false)
	Integer rewardpoints;
	
	public Buyer() {
		super();
	}

	public String getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
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

	public Boolean getIsprivileged() {
		return isprivileged;
	}

	public void setIsprivileged(Boolean isprivileged) {
		this.isprivileged = isprivileged;
	}

	public Integer getRewardpoints() {
		return rewardpoints;
	}

	public void setRewardpoints(Integer rewardpoints) {
		this.rewardpoints = rewardpoints;
	}

	public Buyer(String buyerid, String name, String email, String phoneno, String password, Boolean isactive,
			Boolean isprivileged, Integer rewardpoints) {
		super();
		this.buyerid = buyerid;
		this.name = name;
		this.email = email;
		this.phoneno = phoneno;
		this.password = password;
		this.isactive = isactive;
		this.isprivileged = isprivileged;
		this.rewardpoints = rewardpoints;
	}

	@Override
	public String toString() {
		return "Buyer [buyerid=" + buyerid + ", name=" + name + ", email=" + email + ", phoneno=" + phoneno
				+ ", password=" + password + ", isactive=" + isactive + ", isprivileged=" + isprivileged
				+ ", rewardpoints=" + rewardpoints + "]";
	}
	
}
