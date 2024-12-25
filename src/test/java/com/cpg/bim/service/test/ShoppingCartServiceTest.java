package com.cpg.bim.service.test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
 
import java.util.Arrays;
import java.util.List;
 
import com.cpg.bim.entity.ShoppingCart;
import com.cpg.bim.exception.InvalidIsbnException;
import com.cpg.bim.exception.ShoppingCartAlreadyExistsException;
import com.cpg.bim.exception.ShoppingCartNotFoundException;
import com.cpg.bim.repository.BookRepository;
import com.cpg.bim.repository.ShoppingCartRepository;
import com.cpg.bim.service.ShoppingCartService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
public class ShoppingCartServiceTest {
 
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
 
    @Mock
    private BookRepository bookRepository;
 
    @InjectMocks
    private ShoppingCartService shoppingCartService;
 
    private ShoppingCart shoppingCart;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingCart = new ShoppingCart(1, "1234567890123");
    }
 
    @Test
    public void testAddShoppingCart_valid() {
        // Setup mocks
        when(bookRepository.existsById("1234567890123")).thenReturn(true);  // ISBN exists in bookRepository
        when(shoppingCartRepository.findByUserId(1)).thenReturn(Arrays.asList());  // No cart entry for userId 1
 
        // Run service method
        ResponseEntity<String> response = shoppingCartService.addShoppingCart(shoppingCart);
 
        // Validate the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Shopping cart added successfully.", response.getBody());
        verify(shoppingCartRepository, times(1)).save(shoppingCart);  // Ensure save was called once
    }
 
    @Test
    public void testAddShoppingCart_invalidIsbn() {
        // Setup mocks
        when(bookRepository.existsById("1234567890123")).thenReturn(false);  // ISBN does not exist in bookRepository
 
        // Run service method and expect an exception
        InvalidIsbnException thrown = assertThrows(InvalidIsbnException.class, () -> {
            shoppingCartService.addShoppingCart(shoppingCart);
        });
 
        // Validate the exception message
        assertEquals("Invalid ISBN: 1234567890123", thrown.getMessage());
    }
 
    @Test
    public void testAddShoppingCart_alreadyExists() {
        // Setup mocks
        when(bookRepository.existsById("1234567890123")).thenReturn(true);  // ISBN exists in bookRepository
        when(shoppingCartRepository.findByUserId(1)).thenReturn(Arrays.asList(shoppingCart));  // Cart already exists for userId 1
 
        // Run service method and expect an exception
        ShoppingCartAlreadyExistsException thrown = assertThrows(ShoppingCartAlreadyExistsException.class, () -> {
            shoppingCartService.addShoppingCart(shoppingCart);
        });
 
        // Validate the exception message
        assertEquals("Shopping cart entry already exists for the user and ISBN.", thrown.getMessage());
    }
 
    @Test
    public void testGetBooksByUserId_notFound() {
        // Setup mocks
        when(shoppingCartRepository.findByUserId(1)).thenReturn(Arrays.asList());  // No cart entries for userId 1
 
        // Run service method and expect an exception
        ShoppingCartNotFoundException thrown = assertThrows(ShoppingCartNotFoundException.class, () -> {
            shoppingCartService.getBooksByUserId(1);
        });
 
        // Validate the exception message
        assertEquals("No shopping cart entries found for user ID 1", thrown.getMessage());
    }
 
    @Test
    public void testGetBooksByUserId_success() {
        // Setup mocks
        when(shoppingCartRepository.findByUserId(1)).thenReturn(Arrays.asList(shoppingCart));  // Cart exists for userId 1
 
        // Run service method
        ResponseEntity<List<ShoppingCart>> response = shoppingCartService.getBooksByUserId(1);
 
        // Validate the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());  // Ensure one cart entry is returned
    }
 
    @Test
    public void testUpdateIsbn_invalidIsbn() {
        // Setup mocks
        when(bookRepository.existsById("9876543210987")).thenReturn(false);  // ISBN does not exist in bookRepository
 
        // Run service method and expect an exception
        InvalidIsbnException thrown = assertThrows(InvalidIsbnException.class, () -> {
            shoppingCartService.updateIsbn(1, "9876543210987");
        });
 
        // Validate the exception message
        assertEquals("Invalid ISBN: 9876543210987", thrown.getMessage());
    }
 
    @Test
    public void testUpdateIsbn_notFound() {
        // Setup mocks
        when(bookRepository.existsById("9876543210987")).thenReturn(true);  // ISBN exists in bookRepository
        when(shoppingCartRepository.findByUserId(1)).thenReturn(Arrays.asList());  // No cart entries for userId 1
 
        // Run service method and expect an exception
        ShoppingCartNotFoundException thrown = assertThrows(ShoppingCartNotFoundException.class, () -> {
            shoppingCartService.updateIsbn(1, "9876543210987");
        });
 
        // Validate the exception message
        assertEquals("No shopping cart entries found for user ID 1", thrown.getMessage());
    }
 
    @Test
    public void testUpdateIsbn_success() {
        // Setup mocks
        when(bookRepository.existsById("9876543210987")).thenReturn(true);  // ISBN exists in bookRepository
        when(shoppingCartRepository.findByUserId(1)).thenReturn(Arrays.asList(shoppingCart));  // Cart exists for userId 1
 
        // Run service method
        ResponseEntity<ShoppingCart> response = shoppingCartService.updateIsbn(1, "9876543210987");
 
        // Validate the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("9876543210987", response.getBody().getIsbn());  // Ensure the ISBN is updated
    }
}