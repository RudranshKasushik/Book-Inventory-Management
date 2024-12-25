package com.cpg.bim.service;
 
import com.cpg.bim.entity.Inventory;
import com.cpg.bim.entity.Book;
import com.cpg.bim.entity.Bookcondition;
import com.cpg.bim.exception.InventoryAlreadyExistsException;
import com.cpg.bim.exception.InventoryNotFoundException;
import com.cpg.bim.repository.InventoryRepository;
import com.cpg.bim.repository.BookConditionRepository;
import com.cpg.bim.repository.BookRepository;
//import com.cpg.bim.repository.BookconditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
 
@Service
public class InventoryService {
 
    @Autowired
    private InventoryRepository inventoryRepository;
 
    @Autowired
    private BookRepository bookRepository;
 
    @Autowired
    private BookConditionRepository bookconditionRepository;
 
    public String addInventory(Inventory inventory) {
        Optional<Inventory> existingInventory = inventoryRepository.findById(inventory.getInventoryid());
        if (existingInventory.isPresent()) {
            throw new InventoryAlreadyExistsException("Inventory already exists with this ID");
        }
        Book existingBook = bookRepository.findById(inventory.getBook().getISBN()).orElse(null);
        if (existingBook == null) {
            throw new InventoryNotFoundException("Book not found with ISBN: " + inventory.getBook().getISBN());
        }
        inventory.setBook(existingBook);
        Bookcondition existingBookCondition = bookconditionRepository.findById(inventory.getBookcondition().getRanks()).orElse(null);
        if (existingBookCondition == null) {
            throw new InventoryNotFoundException("Book condition not found with rank: " + inventory.getBookcondition().getRanks());
        }
        inventory.setBookcondition(existingBookCondition);
 
        inventoryRepository.save(inventory);
 
        return "Inventory added successfully";
    }
 
    // Get an Inventory by its ID
    public Inventory getInventoryById(int inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory with ID " + inventoryId + " not found"));
    }
 
    // Update the purchased status of an Inventory by its ID
    public void updatePurchasedStatus(int inventoryId, boolean purchased) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory with ID " + inventoryId + " not found"));
        inventory.setPurchased(purchased);
        inventoryRepository.save(inventory);
    }
    
  
   
// Get list of inventory IDs, ranks, and purchased status for a given ISBN
    public List<Map<String, Object>> getInventoryDetailsByIsbn(String isbn) {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream()
                .filter(inventory -> inventory.getBook().getISBN().equals(isbn))
                .map(inventory -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("inventoryId", inventory.getInventoryid());
                    result.put("ranks", inventory.getBookcondition().getRanks());
                    result.put("purchased", inventory.isPurchased());
                    return result;
                })
                .collect(Collectors.toList());
    }
}