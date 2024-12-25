package com.cpg.bim.controller;
 
import com.cpg.bim.entity.Inventory;
//import com.cpg.bim.exception.InventoryAddedSuccessfullyException;
import com.cpg.bim.exception.InventoryAlreadyExistsException;
import com.cpg.bim.exception.InventoryNotFoundException;
import com.cpg.bim.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
 
@CrossOrigin(origins = "http://localhost:4200") 
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
 
    private final InventoryService inventoryService;
 
    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
 
    // Add a new Inventory
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addInventory(@RequestBody Inventory inventory) {
        try {
            inventoryService.addInventory(inventory);
            Map<String, String> response = new HashMap<>();
            response.put("code", "SUCCESS");
            response.put("message", "Inventory added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } 
         catch (InventoryAlreadyExistsException e) {
            Map<String, String> response = new HashMap<>();
            response.put("code", "ADDFAILS");
            response.put("message", "Inventory already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);  // 409 CONFLICT
        }
    }
 
    // Get an Inventory by its ID
    @GetMapping("/update/purchased/{inventoryId}")
    public ResponseEntity<Inventory> getInventoryForUpdate(@PathVariable int inventoryId) {
        try {
            Inventory inventory =  inventoryService.getInventoryById(inventoryId);
            return ResponseEntity.ok(inventory);
        } catch (InventoryNotFoundException e) {
        	 throw new InventoryNotFoundException("Inventory with ID " + inventoryId + " not found");
        }
    }
 
// Update the purchased status of an Inventory by its ID
    @PutMapping("/{inventoryId}")
    public ResponseEntity<Map<String, String>> updatePurchasedStatus(
        @PathVariable int inventoryId, 
        @RequestParam boolean purchased) {
        try {
            inventoryService.updatePurchasedStatus(inventoryId, purchased);
            Map<String, String> response = new HashMap<>();
            response.put("message", " Purchased status updated successfully");
 
            return ResponseEntity.ok(response); // HTTP 200 OK with the message
        } catch (InventoryNotFoundException e) {
            throw new InventoryNotFoundException("Inventory with ID " + inventoryId + " not found");
        }
    }
// Get list of inventory IDs, ranks, and purchased status by ISBN
    @GetMapping("/inventory-details/{isbn}")
    public ResponseEntity<List<Map<String, Object>>> getInventoryDetailsByIsbn(@PathVariable String isbn) {
        List<Map<String, Object>> inventoryDetails = inventoryService.getInventoryDetailsByIsbn(isbn);
        if (inventoryDetails.isEmpty()) {
            throw new InventoryNotFoundException("No inventory found for ISBN: " + isbn);
        }
        return ResponseEntity.ok(inventoryDetails);
    }
 
 
   
 
    // Handle InventoryAlreadyExistsException
    @ExceptionHandler(InventoryAlreadyExistsException.class)
    public ResponseEntity<String> handleInventoryAlreadyExists(InventoryAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
 
 
    // Handle InventoryNotFoundException
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<String> handleInventoryNotFound(InventoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
 
    // Handle general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}