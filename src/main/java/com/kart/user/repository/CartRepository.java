package com.kart.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kart.user.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

	Optional<Cart> findByBuyeridAndProdid(String buyerid, String prodid);

	List<Cart> findAllByBuyerid(String buyerid);

	void deleteByBuyeridAndProdid(String buyerid, String prodid);

}
