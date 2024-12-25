package com.cpg.bim.controller.test;
 
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cpg.bim.controller.ShoppingCartController;
import com.cpg.bim.entity.ShoppingCart;
import com.cpg.bim.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
 
import java.util.Arrays;
import java.util.List;
 
public class ShoppingCartControllerTest {
 
    private MockMvc mockMvc;
 
    @Mock
    private ShoppingCartService shoppingCartService;
 
    @InjectMocks
    private ShoppingCartController shoppingCartController;
 
    private ObjectMapper objectMapper;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();
        objectMapper = new ObjectMapper();
    }
 
    @Test
    public void testAddShoppingCart_success() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart(1, "1234567890123");
        when(shoppingCartService.addShoppingCart(any(ShoppingCart.class)))
                .thenReturn(new ResponseEntity<>("Shopping cart added successfully.", HttpStatus.CREATED));
 
        mockMvc.perform(post("/api/shoppingcart/post")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(shoppingCart)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Shopping cart added successfully."));
    }
 
    @Test
    public void testGetBooksByUserId_success() throws Exception {
        List<ShoppingCart> shoppingCartList = Arrays.asList(new ShoppingCart(1, "1234567890123"));
        when(shoppingCartService.getBooksByUserId(1))
                .thenReturn(new ResponseEntity<>(shoppingCartList, HttpStatus.OK));
 
        mockMvc.perform(get("/api/shoppingcart/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].isbn").value("1234567890123"));
    }
 
    @Test
    public void testUpdateIsbn_success() throws Exception {
        ShoppingCart updatedCart = new ShoppingCart(1, "9876543210987");
        when(shoppingCartService.updateIsbn(1, "9876543210987"))
                .thenReturn(new ResponseEntity<>(updatedCart, HttpStatus.OK));
 
        mockMvc.perform(put("/api/shoppingcart/1")
                        .param("newIsbn", "9876543210987"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.isbn").value("9876543210987"));
    }
 
    @Test
    public void testAddShoppingCart_conflict() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart(1, "1234567890123");
        when(shoppingCartService.addShoppingCart(any(ShoppingCart.class)))
                .thenReturn(new ResponseEntity<>("Shopping cart entry already exists for the user and ISBN.", HttpStatus.CONFLICT));
 
        mockMvc.perform(post("/api/shoppingcart/post")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(shoppingCart)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Shopping cart entry already exists for the user and ISBN."));
    }
 
    @Test
    public void testGetBooksByUserId_notFound() throws Exception {
        when(shoppingCartService.getBooksByUserId(1))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
 
        mockMvc.perform(get("/api/shoppingcart/1"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    public void testUpdateIsbn_notFound() throws Exception {
        when(shoppingCartService.updateIsbn(1, "9876543210987"))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
 
        mockMvc.perform(put("/api/shoppingcart/1")
                        .param("newIsbn", "9876543210987"))
                .andExpect(status().isNotFound());
    }
}