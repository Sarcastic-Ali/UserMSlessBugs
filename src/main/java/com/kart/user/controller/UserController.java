package com.kart.user.controller;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.kart.user.dto.BuyerDTO;
import com.kart.user.dto.CartDTO;
import com.kart.user.dto.LoginDTO;
import com.kart.user.dto.ProductDTO;
import com.kart.user.dto.SellerDTO;
import com.kart.user.dto.WishlistDTO;
import com.kart.user.service.BuyerService;
import com.kart.user.service.SellerService;

@RestController
public class UserController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	
	 @Value("${product.uri}") 
	 String productUri;
	 
	
	//@Autowired 
	//DiscoveryClient client;
	
	@Autowired
	Environment environment;

	@Autowired
	BuyerService buyerService;

	@Autowired
	SellerService sellerService;

	//Register Buyer Details
	@PostMapping(value = "/api/buyer/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createBuyer(@Valid @RequestBody BuyerDTO buyerDTO) throws Exception{
		
		logger.info("Creation request for customer {}", buyerDTO);
		String successMessage = "Added Successfully " + buyerService.saveBuyer(buyerDTO);
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}

	//Get Buyer by buyerid
	@GetMapping(value = "/api/buyer/{buyerid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BuyerDTO> getBuyerById(@PathVariable String buyerid) throws Exception {
		
		try {
			BuyerDTO buyer = buyerService.getBuyerById(buyerid);
			return new ResponseEntity<>(buyer, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	//Login buyer
	@PostMapping(value = "/api/buyer/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean login(@RequestBody LoginDTO loginDTO) {
		
		logger.info("Login request for customer {} with password {}", loginDTO.getEmail(),loginDTO.getPassword());
		return buyerService.login(loginDTO);
	}

	//Delete Buyer account
	@DeleteMapping(value = "/api/buyer/{buyerid}")
	public ResponseEntity<String> deactivateBuyer(@PathVariable String buyerid) throws Exception {
		
		String successMessage = buyerService.deactivateBuyer(buyerid);
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}

	//Adding products to cart
	@PostMapping(value = "/api/cart/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addCart(@RequestBody CartDTO cartDTO) throws Exception {
		
		//List<ServiceInstance> Pinstances = client.getInstances("ProductMS");
		//ServiceInstance Pinstance= Pinstances.get(0);
		//URI productUri = Pinstance.getUri();
		
		ProductDTO prod = new RestTemplate().getForObject(productUri+ "api/productid/" + cartDTO.getProdid(), ProductDTO.class);
		if(prod.getStock() > cartDTO.getQuantity())
		{
		logger.info("Additing to cart", cartDTO);
		String successMessage  = buyerService.addToCart(cartDTO);
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}
		else
		{
			String successMessage  = "Out of Stock";
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}
	}

	//Removing from cart
	@PostMapping(value = "/api/cart/remove/{buyerid}/{prodid}")
	public ResponseEntity<String> RemoveCart(@PathVariable String buyerid, @PathVariable String prodid) throws Exception {
		logger.info("Removing from cart");
		String successMessage  = buyerService.removeFromCart(buyerid, prodid);
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}

	//Moving from wishlist to cart
	@PostMapping(value = "/api/wishlist/move", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> moveToCart(@RequestBody CartDTO cartDTO) throws Exception {
		
		//List<ServiceInstance> Pinstances = client.getInstances("ProductMS");
		//ServiceInstance Pinstance= Pinstances.get(0);
		//URI productUri = Pinstance.getUri(); 
		
		ProductDTO prod = new RestTemplate().getForObject(productUri+ "api/productid/" + cartDTO.getProdid(), ProductDTO.class);
		if(prod.getStock() > cartDTO.getQuantity())
		{
		logger.info("Moving to cart", cartDTO);
		WishlistDTO wishDTO = new WishlistDTO();
		wishDTO.setBuyerid(cartDTO.getBuyerid());
		wishDTO.setProdid(cartDTO.getProdid());
		String successMessage  = buyerService.addToCart(cartDTO);
		buyerService.removeFromWishlist(wishDTO);
		new RestTemplate().patchForObject(productUri + "api/product/"+ cartDTO.getProdid()+"/"+ (~(cartDTO.getQuantity() - 1)), null, String.class);
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
		
		}
		else
		{
			String successMessage  = "Out of Stock";
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}
	}
	
	//get buyer's cart detail
	@GetMapping(value = "/api/cart/{buyerid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CartDTO>> getCartDetail(@PathVariable String buyerid) {
		logger.info("Fetching all Buyers");
		return new ResponseEntity<>(buyerService.getCart(buyerid),HttpStatus.OK);
	}

	//Adding to wishlist
	@PostMapping(value = "/api/wishlist/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addWishlist(@RequestBody WishlistDTO wishDTO) throws Exception {
		
		logger.info("Additing to Wishlist ", wishDTO);
		String successMessage  = buyerService.addToWishlist(wishDTO);
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}

	//Deleting from the wishlist
	@DeleteMapping(value = "/api/wishlist/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> RemoveFromWishlist(@RequestBody WishlistDTO wishDTO) throws Exception {
		
		logger.info("Removing from Wishlist {}", wishDTO);
		String successMessage  = buyerService.removeFromWishlist(wishDTO);
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}


	//Seller	

	//Register Seller Details
	@PostMapping(value = "/api/seller/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createSeller(@Valid @RequestBody SellerDTO sellerDTO) throws Exception{
		
		logger.info("Creation request for Seller {}", sellerDTO);
		String st = "Seller account created successfully" + sellerService.saveSeller(sellerDTO);
		return new ResponseEntity<>(st, HttpStatus.OK);
	}

	//All sellers details
	@GetMapping(value = "/api/sellers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SellerDTO> getAllSeller() {
		
		logger.info("Fetching all sellers {}");
		return sellerService.getAllSeller();
	}

	//seller login
	@PostMapping(value = "/api/seller/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean Login(@RequestBody LoginDTO loginDTO) {
		
		logger.info("Login request for seller {} with password {}", loginDTO.getEmail(),loginDTO.getPassword());
		return sellerService.login(loginDTO);
	}


	//Delete seller account
	@DeleteMapping(value = "/api/seller/{sellerid}")
	public ResponseEntity<String> deleteSeller(@PathVariable String sellerid) throws Exception {
		
		String successMessage  = sellerService.deleteSeller(sellerid);
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}


	//Getting All buyers details
	@GetMapping(value = "/api/buyers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BuyerDTO> getAllBuyer() {
		
		logger.info("Fetching all Buyers");
		return buyerService.getAllBuyer();
	}
	
	//update reward points
	@PutMapping(value = "/api/user/updateRewardPoints/{buyerId}/{rewPoints}")
	public ResponseEntity<String> updateRewardPoints(@PathVariable String buyerId, @PathVariable Integer rewPoints)
	{
		try {
			String msg = buyerService.updateRewardPoint(buyerId, rewPoints);
			return new ResponseEntity<>(msg,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
		}
	}

}
