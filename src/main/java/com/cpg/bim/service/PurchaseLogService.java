package com.cpg.bim.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cpg.bim.entity.Inventory;
import com.cpg.bim.entity.PurchaseLog;
import com.cpg.bim.entity.PurchaseLogDTO;
import com.cpg.bim.entity.PurchaseLogID;
import com.cpg.bim.entity.Users;
import com.cpg.bim.exception.InventoryAlreadyTakenException;
import com.cpg.bim.exception.InventoryNotFoundException;
import com.cpg.bim.exception.PurchaseLogAlreadyExistsException;
import com.cpg.bim.exception.PurchaseLogNotFoundException;
import com.cpg.bim.exception.UserNotFoundException;
import com.cpg.bim.repository.InventoryRepository;
import com.cpg.bim.repository.PurchaseLogRepository;
import com.cpg.bim.repository.UserRepository;
//import com.cpg.bim.repository.UsersRepository;
 
@Service
public class PurchaseLogService 
{
	   @Autowired
	   PurchaseLogRepository purchaselogRepository;
	   @Autowired
	   UserRepository usersRepository;
	   @Autowired
	   InventoryRepository inventoryRepository;
 
	   @Transactional
	   public PurchaseLog addPurchaseLog(PurchaseLogDTO purchaseLogDTO) 
	   {
	       PurchaseLog purchaseLog = new PurchaseLog();
 
	    // Check if the combination of userId and inventoryId already exists
	        Optional<PurchaseLog> existingLog = purchaselogRepository.findByUsers_UseridAndInventory_Inventoryid(purchaseLogDTO.getUserId(),  purchaseLogDTO.getInventoryId());
	        if (existingLog.isPresent()) 
	        {
	        	throw new PurchaseLogAlreadyExistsException(
	                    "Purchase log entry already exists for user ID: " + purchaseLogDTO.getUserId() +
	                            " and inventory ID: " + purchaseLogDTO.getInventoryId());
	        }
       // Set the ID for the PurchaseLog entity
           PurchaseLogID purchaseLogId = new PurchaseLogID(purchaseLogDTO.getUserId(), purchaseLogDTO.getInventoryId());
           purchaseLog.setId(purchaseLogId);
 
       // Ensure valid userId and inventoryId exist. Retrieve the User and Inventory entities using the IDs from the DTO
           Users user = usersRepository.findById(purchaseLogDTO.getUserId()).orElseThrow( ()-> new UserNotFoundException("User with ID " + purchaseLogDTO.getUserId() + " not found."));
           Inventory inventory = inventoryRepository.findById(purchaseLogDTO.getInventoryId()).orElseThrow(() -> new InventoryNotFoundException(
                   "Inventory with ID " + purchaseLogDTO.getInventoryId() + " not found."));
      // Check if inventory is already purchased (cannot be shared)
           Optional<PurchaseLog> existingInventoryLog = purchaselogRepository.findByInventory_InventoryId(purchaseLogDTO.getInventoryId());
           if (existingInventoryLog.isPresent()) 
           {
               throw new PurchaseLogAlreadyExistsException(
                   "Inventory ID " + purchaseLogDTO.getInventoryId() + " is already purchased by another user.");
           }
       // Set the related entities in PurchaseLog
           purchaseLog.setUser(user);
           purchaseLog.setInventory(inventory);
 
       // Save and return the PurchaseLog entity
          return purchaselogRepository.save(purchaseLog);
	  }
	   // GET 
	   @Transactional(readOnly = true)
	   public List<PurchaseLog> getPurchaseLogsByUserId(int userId) 
	   {
	       //return purchaselogRepository.findByUserId(userId);
		   List<PurchaseLog> logs = purchaselogRepository.findByUserId(userId);
	        if (logs.isEmpty()) {
	            throw new PurchaseLogNotFoundException("No purchase logs found for user ID: " + userId);
	        }
 
	        return logs;
	   }
 
	   // UPDATE 
	   @Transactional
	   public ResponseEntity<Object> updateInventoryId(int userId, int newInventory) 
	   {
		// Validate if the new inventory exists
	        if (!inventoryRepository.existsById(newInventory)) {
	            throw new InventoryNotFoundException("Inventory with ID " + newInventory + " not found.");
	        }
	   // Ensure the new inventory is not already assigned to another user (inventory must be unique)
	        Optional<PurchaseLog> existingInventoryLog = purchaselogRepository.findByInventory_InventoryId(newInventory);
	        if (existingInventoryLog.isPresent() && existingInventoryLog.get().getUser().getUserid() != userId) {
	            // Inventory ID is taken by another user
	            String errorMessage = "Inventory ID " + newInventory + " is already taken by another user.";
	            throw new InventoryAlreadyTakenException(errorMessage);  // Throw exception instead of returning ResponseEntity
	        }
	       // Update the inventory ID based only on the user ID
	       int rowsAffected = purchaselogRepository.updateInventoryByUser(userId, newInventory);
	       if (rowsAffected > 0) 
	       {
	           // Fetch the updated PurchaseLog
	           List<PurchaseLog> updatedLogs = purchaselogRepository.findByUserId(userId);
	           // Return the first updated log (assuming only one log per user)
	           PurchaseLog updatedLog = updatedLogs.isEmpty() ? null : updatedLogs.get(0);
	           // Ensure user is not serialized by setting it to null or by the @JsonIgnore annotations
	           if (updatedLog != null) {
	               updatedLog.setUser(null);  // If necessary, explicitly set to null (to be extra safe)
	           }
	           return ResponseEntity.ok(updatedLog);
	       } 
	       else 
	       {
	    	// No records were affected, so return a message directly
	           return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                .body("No records found to update."); // Return 404 with the message
	       }
	   }
 
 
}