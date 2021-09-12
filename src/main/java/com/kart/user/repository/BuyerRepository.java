package com.kart.user.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kart.user.entity.Buyer;
@Repository
public interface BuyerRepository extends JpaRepository<Buyer, String> {

	Buyer findByEmail(String Email);

	Optional<Buyer> findByphoneno(String phoneno);
}
