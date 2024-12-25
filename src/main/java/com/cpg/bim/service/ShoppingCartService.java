package com.cpg.bim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.cpg.bim.entity.Book;
import com.cpg.bim.entity.ShoppingCart;
import com.cpg.bim.exception.InvalidIsbnException;
import com.cpg.bim.exception.ShoppingCartAlreadyExistsException;
import com.cpg.bim.exception.ShoppingCartNotFoundException;
import com.cpg.bim.repository.BookRepository;
import com.cpg.bim.repository.ShoppingCartRepository;
 
 
@Service
public class ShoppingCartService 
{
	@Autowired 
	   ShoppingCartRepository shoppingcartRepository;
	   @Autowired
	   BookRepository bookRepository;
	   @Transactional
	   public ResponseEntity<String> addShoppingCart(ShoppingCart shoppingCart)
	   {
		   // Validate if ISBN exists in the Book table
	       if (!bookRepository.existsById(shoppingCart.getIsbn())) {
	           throw new InvalidIsbnException("Invalid ISBN: " + shoppingCart.getIsbn());
	       }
	       // Check if cart entry already exists
	       if (shoppingcartRepository.findByUserId(shoppingCart.getUserId()).stream()
	               .anyMatch(cart -> cart.getIsbn().equals(shoppingCart.getIsbn()))) {
	           throw new ShoppingCartAlreadyExistsException("Shopping cart entry already exists for the user and ISBN.");
	       }
	       // Save the new cart entry
	       shoppingcartRepository.save(shoppingCart);
	       return ResponseEntity.status(HttpStatus.CREATED).body("Shopping cart added successfully.");
	   }
	   @Transactional(readOnly = true)
	   public ResponseEntity<List<ShoppingCart>> getBooksByUserId(int userId) 
	   {
	       List<ShoppingCart> books = shoppingcartRepository.findByUserId(userId);
	       if (books.isEmpty())
	       {
	    	   throw new ShoppingCartNotFoundException("No shopping cart entries found for user ID " + userId);
	       }
	       return ResponseEntity.ok(books);  // Return the list of books
	   }
	   @Transactional
	   public ResponseEntity<ShoppingCart> updateIsbn(int userId, String newIsbn) 
	   {
		   // Validate if ISBN exists in the Book table
	       if (!bookRepository.existsById(newIsbn)) 
	       {
	           throw new InvalidIsbnException("Invalid ISBN: " + newIsbn);
	       }
		   // Find shopping cart entries for the user
	       List<ShoppingCart> cartList = shoppingcartRepository.findByUserId(userId);
	       if (cartList.isEmpty()) 
	       {
	    	   throw new ShoppingCartNotFoundException("No shopping cart entries found for user ID " + userId);
	       }
	       // Assuming the first entry to be updated
	       ShoppingCart cart = cartList.get(0);
	       cart.setIsbn(newIsbn.trim());
	       // Save the updated cart entry
	       shoppingcartRepository.save(cart);
	       return ResponseEntity.ok(cart);  // Return the updated shopping cart entry
	   }
	}