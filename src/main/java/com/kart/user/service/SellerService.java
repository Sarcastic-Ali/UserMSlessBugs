package com.kart.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kart.user.dto.LoginDTO;
import com.kart.user.dto.SellerDTO;
import com.kart.user.entity.Seller;
import com.kart.user.repository.SellerRepository;

@Service
@Transactional
public class SellerService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SellerRepository sellerrepo;
    
    //Create Seller
    public String saveSeller(SellerDTO sellerDTO) throws Exception{
    	Optional<Seller> optional = sellerrepo.findByphoneno(sellerDTO.getPhoneno());
		if(optional.isPresent()) {
			throw new Exception("Number Already Exist");
		}
        logger.info("Creation request for customer {} with data {}",sellerDTO);
        Seller seller = sellerDTO.createSeller();
        Seller s2 =sellerrepo.save(seller);
        return s2.getSellerid();
    }
    
    //Get all sellers
	public List<SellerDTO> getAllSeller() {

		List<Seller> sellers = sellerrepo.findAll();
		List<SellerDTO> sellerDTOs = new ArrayList<>();

		for (Seller seller : sellers) {
			SellerDTO sellerDTO = SellerDTO.valueOf(seller);
			sellerDTOs.add(sellerDTO);
		}

		logger.info("Product Details : {}", sellerDTOs);
		return sellerDTOs;
	}
	
	//Login
	public boolean login(LoginDTO loginDTO) {
		logger.info("Login request for customer {} with password {}", loginDTO.getEmail(),loginDTO.getPassword());
		Seller sell = sellerrepo.findByEmail(loginDTO.getEmail());
		if (sell != null && sell.getPassword().equals(loginDTO.getPassword()) && sell.getIsactive()) {
			return true;
		}
		return false;
	}
	
	//Deactivate seller account
	public String deleteSeller(String sellerid) throws Exception {
		Optional<Seller> seller = sellerrepo.findById(sellerid);
		Seller s = seller.orElseThrow(() -> new Exception("Service.Seller_NOT_FOUND"));
		s.setIsactive(false);
		return "Deactivated seller successfully";
	}
}

