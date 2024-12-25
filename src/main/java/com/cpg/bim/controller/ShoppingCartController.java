 package com.cpg.bim.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.cpg.bim.entity.ShoppingCart;
import com.cpg.bim.service.ShoppingCartService;
 
@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from this origin
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController 
{
	@Autowired
    private ShoppingCartService shoppingcartService;
	@PostMapping("/add/post")
    public ResponseEntity<String> addShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingcartService.addShoppingCart(shoppingCart);
    }
	
	
	 @GetMapping("/{userId}")
	    public ResponseEntity<List<ShoppingCart>> getBooksByUserId(@PathVariable int userId)
	    {
	        return shoppingcartService.getBooksByUserId(userId);
	    }
	 @PutMapping("/{userId}")
	 public ResponseEntity<ShoppingCart> updateIsbn(@PathVariable int userId, @RequestParam String newIsbn)
	 {
	        return shoppingcartService.updateIsbn(userId, newIsbn);
	 }
}