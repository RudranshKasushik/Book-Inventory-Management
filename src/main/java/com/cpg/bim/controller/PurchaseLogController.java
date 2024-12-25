package com.cpg.bim.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cpg.bim.entity.PurchaseLog;
import com.cpg.bim.entity.PurchaseLogDTO;
import com.cpg.bim.service.PurchaseLogService;
 
@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/purchaselog")
public class PurchaseLogController
{
 
	@Autowired
    PurchaseLogService purchaselogService;
  /* combination of userid and inventoryid is
    being checked for uniqueness in the purchaselog table */ 
    @PostMapping(value="/post",  consumes = "application/json")
    public ResponseEntity<String> addPurchaseLog(@RequestBody PurchaseLogDTO purchaseLogDTO)
    {
            purchaselogService.addPurchaseLog(purchaseLogDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Purchase Log added successfully");
    }
    @GetMapping(value="/{userid}")
    public ResponseEntity<List<PurchaseLog>> getPurchaseLogs(@PathVariable int userid)
    {
        List<PurchaseLog> purchaseLogs = purchaselogService.getPurchaseLogsByUserId(userid);
        return ResponseEntity.ok(purchaseLogs);  // Return ResponseEntity with status 200
    }
    @PutMapping(value = "/update/inventoryid/{userId}")
    public ResponseEntity<Object> updateInventory(@PathVariable("userId") int userId, @RequestBody PurchaseLogDTO purchaseLogDTO) 
    {
    	ResponseEntity<Object> response = purchaselogService.updateInventoryId( purchaseLogDTO.getUserId(),
                                                                                     purchaseLogDTO.getNewInventory() );
        return response;
    }
}