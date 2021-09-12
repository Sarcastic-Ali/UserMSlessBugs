package com.kart.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kart.user.dto.BuyerDTO;
import com.kart.user.dto.CartDTO;
import com.kart.user.dto.LoginDTO;
import com.kart.user.dto.WishlistDTO;
import com.kart.user.entity.Buyer;
import com.kart.user.entity.Cart;
import com.kart.user.entity.Wishlist;
import com.kart.user.repository.BuyerRepository;
import com.kart.user.repository.CartRepository;
import com.kart.user.repository.WishlistRepository;

@Service
@Transactional
public class BuyerService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	BuyerRepository buyerRepo;

	@Autowired
	CartRepository cartRepo;

	@Autowired
	WishlistRepository wishRepo;


	//Creates new Buyer
	public String saveBuyer(BuyerDTO buyerDTO) throws Exception{
		Optional<Buyer> optional = buyerRepo.findByphoneno(buyerDTO.getPhoneno());
		if(optional.isPresent()) {
			throw new Exception("Number Already Exist");
		}
		logger.info("Creation request for customer {} with data {}",buyerDTO);
		Buyer buyer = buyerDTO.createBuyer();
		Buyer buyer2 = buyerRepo.save(buyer);
		return buyer2.getBuyerid();
	}
	
	//Get buyer by id
	public BuyerDTO getBuyerById(String buyerId) throws Exception {
		BuyerDTO buyerDTO = null;
		logger.info("Profile request for buyer {}", buyerId);
		Optional<Buyer> optBuyer = buyerRepo.findById(buyerId);
		if (optBuyer.isPresent()) {
			Buyer buyer = optBuyer.get();
			buyerDTO = BuyerDTO.valueOf(buyer);
		} else {
			throw new Exception("Service.BUYERS_NOT_FOUND");
		}
		logger.info("Profile for buyer : {}", buyerDTO);
		return buyerDTO;
	}

	//Fetches all buyers details
	public List<BuyerDTO> getAllBuyer() {

		List<Buyer> buyers = buyerRepo.findAll();
		List<BuyerDTO> buyerDTOs = new ArrayList<>();

		for (Buyer buyer : buyers) {
			BuyerDTO buyerDTO = BuyerDTO.valueOf(buyer);
			buyerDTOs.add(buyerDTO);
		}

		logger.info("Product Details : {}", buyerDTOs);
		return buyerDTOs;
	}

	//Login check
	public boolean login(LoginDTO loginDTO) {
		logger.info("Login request for customer {} with password {}", loginDTO.getEmail(),loginDTO.getPassword());
		Buyer buy = buyerRepo.findByEmail(loginDTO.getEmail());
		if (buy != null && buy.getPassword().equals(loginDTO.getPassword())) {
			return true;
		}
		return false;
	}

	//Deactivates buyer account
	public String deactivateBuyer(String buyerid) throws Exception {
		Optional<Buyer> buyer = buyerRepo.findById(buyerid);
		Buyer buyer1 = buyer.orElseThrow(() -> new Exception("Service.Buyer_NOT_FOUND"));
		buyer1.setIsactive(false);
		return "Deactivated buyer successfully";
	}

	//Add to cart
	public String addToCart(CartDTO cartDTO)  {
		
		Cart cart = cartDTO.createEntity();
		cartRepo.save(cart);
		return "Added to the Cart";
	}

	//Remove from cart
	public String removeFromCart(String buyerid, String prodid) throws Exception{
		Optional<Cart> cart = cartRepo.findByBuyeridAndProdid(buyerid, prodid);
		cart.orElseThrow(() -> new Exception("No data Found with given user data"));
		cartRepo.deleteByBuyeridAndProdid(buyerid , prodid);
		return "Removed from the cart";
	}

	//Add to wishlist
	public String addToWishlist(WishlistDTO wishDTO)  {
		Wishlist wish = wishDTO.createEntity();
		wishRepo.save(wish);
		return "Added to the wishlist";
	}

	//Move from wishlist to cart
	public String moveFromWishlistToCart(CartDTO cartDTO) throws Exception{
		Optional<Wishlist> wish = wishRepo.findByBuyeridAndProdid(cartDTO.getBuyerid(), cartDTO.getProdid());
		wish.orElseThrow(() -> new Exception("No data Found with given user data"));
		Cart cart = cartDTO.createEntity();
		cartRepo.save(cart);
		wishRepo.deleteByBuyeridAndProdid(cartDTO.getBuyerid(), cartDTO.getProdid());
		return "Added to the cart";
	}

	//Remove from wishlist
	public String removeFromWishlist(WishlistDTO wishDTO) throws Exception{
		Optional<Wishlist> wish = wishRepo.findByBuyeridAndProdid(wishDTO.getBuyerid(),wishDTO.getProdid());
		wish.orElseThrow(() -> new Exception("No data Found with given user data"));
		wishRepo.deleteByBuyeridAndProdid(wishDTO.getBuyerid(),wishDTO.getProdid());
		return "Removed from the wishlist";
	}

	//get cart details
	public List<CartDTO> getCart(String buyerid) {
		List<Cart> cart = cartRepo.findAllByBuyerid(buyerid);
		List<CartDTO> cartdto = new ArrayList<>();
		for (Cart c : cart) {
			CartDTO b = CartDTO.valueOf(c);
			cartdto.add(b);
		}
		return cartdto;
	}

	public String updateRewardPoint(String buyerId, Integer rewPoints) throws Exception {
		Optional<Buyer> buy = buyerRepo.findById(buyerId);
		Buyer p = buy.orElseThrow(() -> new Exception("Not Found"));
		int repoints = p.getRewardpoints() + rewPoints;
		p.setRewardpoints(repoints);
		return null;
	}

}
