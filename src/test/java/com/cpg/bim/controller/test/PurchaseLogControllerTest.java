package com.cpg.bim.controller.test;

import com.cpg.bim.controller.PurchaseLogController;
import com.cpg.bim.entity.PurchaseLog;
import com.cpg.bim.entity.PurchaseLogDTO;
import com.cpg.bim.entity.PurchaseLogID;
import com.cpg.bim.exception.PurchaseLogAlreadyExistsException;
import com.cpg.bim.service.PurchaseLogService;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import java.util.List;
 
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
 
@ExtendWith(MockitoExtension.class)
public class PurchaseLogControllerTest {
 
    @Mock
    private PurchaseLogService purchaselogService;
 
    @InjectMocks
    private PurchaseLogController purchaselogController;
 
    private PurchaseLogDTO purchaseLogDTO;
 
    @BeforeEach
    public void setup() {
        purchaseLogDTO = new PurchaseLogDTO(1, 101, 200); // Sample data
    }
 
    @Test
    public void testAddPurchaseLog() {
        // Arrange
        PurchaseLog purchaseLog = new PurchaseLog();
        purchaseLog.setId(new PurchaseLogID(1, 200));  // Mock the returned PurchaseLog
        when(purchaselogService.addPurchaseLog(purchaseLogDTO)).thenReturn(purchaseLog);
 
        // Act: Call the controller method and capture the response
        ResponseEntity<String> response = purchaselogController.addPurchaseLog(purchaseLogDTO);
 
        // Assert: Check the status code is 201 CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Purchase Log added successfully", response.getBody());
 
        // Verify the service method was called
        verify(purchaselogService, times(1)).addPurchaseLog(purchaseLogDTO);
    }
 
    @Test
    public void testGetPurchaseLogs() {
        // Arrange
        int userId = 1;  // Use a valid userId
        PurchaseLog purchaseLog = new PurchaseLog();
        purchaseLog.setId(new PurchaseLogID(userId, 100)); // Set some properties
 
        when(purchaselogService.getPurchaseLogsByUserId(userId)).thenReturn(List.of(purchaseLog));
 
        // Act: Call the controller method and capture the response
        ResponseEntity<List<PurchaseLog>> response = purchaselogController.getPurchaseLogs(userId);
 
        // Assert
        assertNotNull(response); // Check that the response is not null
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check if the response has 200 OK status
        assertEquals(1, response.getBody().size()); // Check if the list has exactly one item
        assertEquals(userId, response.getBody().get(0).getId().getUserId()); // Check if the userId is correct in the response
    }
 
    @Test
    public void testUpdateInventory() {
        // Arrange
        int userId = 1;
        int newInventoryId = 200;
        PurchaseLogDTO updateDTO = new PurchaseLogDTO(userId, 101, newInventoryId);
        when(purchaselogService.updateInventoryId(userId, newInventoryId)).thenReturn(ResponseEntity.ok(new PurchaseLog()));
 
        // Act: Call the controller method and capture the response
        ResponseEntity<Object> response = purchaselogController.updateInventory(userId, updateDTO);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
 
        verify(purchaselogService, times(1)).updateInventoryId(userId, newInventoryId);
    }
 
    @Test
    public void testUpdateInventory_NotFound() {
        // Arrange
        int userId = 1;
        int newInventoryId = 200;
        PurchaseLogDTO updateDTO = new PurchaseLogDTO(userId, 101, newInventoryId);
        when(purchaselogService.updateInventoryId(userId, newInventoryId))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory not found"));
 
        // Act: Call the controller method and capture the response
        ResponseEntity<Object> response = purchaselogController.updateInventory(userId, updateDTO);
 
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Inventory not found", response.getBody());
 
        verify(purchaselogService, times(1)).updateInventoryId(userId, newInventoryId);
    }
 
    @Test
    public void testAddPurchaseLog_Conflict() {
        // Arrange
        doThrow(new PurchaseLogAlreadyExistsException("Purchase log already exists")).when(purchaselogService).addPurchaseLog(purchaseLogDTO);
 
        // Act and Assert
        try {
            purchaselogController.addPurchaseLog(purchaseLogDTO);
            fail("Exception not thrown");
        } catch (Exception e) {
            assertTrue(e instanceof PurchaseLogAlreadyExistsException);
            assertEquals("Purchase log already exists", e.getMessage());
        }
 
        verify(purchaselogService, times(1)).addPurchaseLog(purchaseLogDTO);
    }
}