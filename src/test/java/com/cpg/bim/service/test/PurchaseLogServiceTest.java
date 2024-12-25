package com.cpg.bim.service.test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
 
import com.cpg.bim.entity.*;
import com.cpg.bim.exception.*;
import com.cpg.bim.repository.InventoryRepository;
import com.cpg.bim.repository.PurchaseLogRepository;
import com.cpg.bim.repository.UserRepository;
import com.cpg.bim.service.PurchaseLogService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
 
import java.util.*;
 
class PurchaseLogServiceTest {
 
    @Mock
    private PurchaseLogRepository purchaseLogRepository;
 
    @Mock
    private UserRepository usersRepository;
 
    @Mock
    private InventoryRepository inventoryRepository;
 
    @InjectMocks
    private PurchaseLogService purchaseLogService;
 
    private PurchaseLogDTO purchaseLogDTO;
    private Users user;
    private Inventory inventory;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
 
        // Mock the DTO
        purchaseLogDTO = new PurchaseLogDTO();
        purchaseLogDTO.setUserId(1);
        purchaseLogDTO.setInventoryId(101);
        purchaseLogDTO.setNewInventory(102);
 
        // Mock the User and Inventory
        user = new Users();
        user.setUserid(1);
 
        inventory = new Inventory();
        inventory.setInventoryid(101);
    }
 
    @Test
    void testAddPurchaseLog_Success() {
        when(usersRepository.findById(1)).thenReturn(Optional.of(user));
        when(inventoryRepository.findById(101)).thenReturn(Optional.of(inventory));
        when(purchaseLogRepository.findByUsers_UseridAndInventory_Inventoryid(1, 101)).thenReturn(Optional.empty());
        when(purchaseLogRepository.findByInventory_InventoryId(101)).thenReturn(Optional.empty());
        when(purchaseLogRepository.save(any(PurchaseLog.class))).thenReturn(new PurchaseLog());
 
        PurchaseLog result = purchaseLogService.addPurchaseLog(purchaseLogDTO);
 
        assertNotNull(result);
        verify(purchaseLogRepository).save(any(PurchaseLog.class));
    }
 
    @Test
    void testAddPurchaseLog_AlreadyExists() {
        when(purchaseLogRepository.findByUsers_UseridAndInventory_Inventoryid(1, 101))
                .thenReturn(Optional.of(new PurchaseLog()));
 
        PurchaseLogAlreadyExistsException exception = assertThrows(PurchaseLogAlreadyExistsException.class, () -> {
            purchaseLogService.addPurchaseLog(purchaseLogDTO);
        });
 
        assertEquals("Purchase log entry already exists for user ID: 1 and inventory ID: 101", exception.getMessage());
    }
 
    @Test
    void testUpdateInventory_Success() {
        when(inventoryRepository.existsById(102)).thenReturn(true);
        when(purchaseLogRepository.findByInventory_InventoryId(102)).thenReturn(Optional.empty());
        when(purchaseLogRepository.updateInventoryByUser(1, 102)).thenReturn(1);
        when(purchaseLogRepository.findByUserId(1)).thenReturn(Collections.singletonList(new PurchaseLog()));
 
        ResponseEntity<Object> response = purchaseLogService.updateInventoryId(1, 102);
 
        assertEquals(200, response.getStatusCodeValue());
    }
 
    @Test
    void testUpdateInventory_InventoryNotFound() {
        when(inventoryRepository.existsById(102)).thenReturn(false);
 
        InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, () -> {
            purchaseLogService.updateInventoryId(1, 102);
        });
 
        assertEquals("Inventory with ID 102 not found.", exception.getMessage());
    }
 
    @Test
    void testUpdateInventory_InventoryAlreadyTaken() {
        // Mock the inventory ID existence
        when(inventoryRepository.existsById(102)).thenReturn(true);
        // Mock that the inventory is already taken by another user
        PurchaseLog existingPurchaseLog = new PurchaseLog();
        Users otherUser = new Users();
        otherUser.setUserid(2);  // Simulate another user having the inventory
        existingPurchaseLog.setUser(otherUser);
        when(purchaseLogRepository.findByInventory_InventoryId(102)).thenReturn(Optional.of(existingPurchaseLog));
 
        // Execute the method and expect an exception
        InventoryAlreadyTakenException exception = assertThrows(InventoryAlreadyTakenException.class, () -> {
            purchaseLogService.updateInventoryId(1, 102);  // Trying to update with the inventory already taken
        });
 
        // Verify the exception message
        assertEquals("Inventory ID 102 is already taken by another user.", exception.getMessage());
    }
 
 
    @Test
    void testUpdateInventory_NoRecordsFound() {
        when(inventoryRepository.existsById(102)).thenReturn(true);
        when(purchaseLogRepository.findByInventory_InventoryId(102)).thenReturn(Optional.empty());
        when(purchaseLogRepository.updateInventoryByUser(1, 102)).thenReturn(0);
 
        ResponseEntity<Object> response = purchaseLogService.updateInventoryId(1, 102);
 
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("No records found to update.", response.getBody());
    }
}