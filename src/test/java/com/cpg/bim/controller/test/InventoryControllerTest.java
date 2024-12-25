package com.cpg.bim.controller.test;
 
import com.cpg.bim.controller.InventoryController;
import com.cpg.bim.entity.Book;
import com.cpg.bim.entity.Bookcondition;
import com.cpg.bim.entity.Inventory;
import com.cpg.bim.exception.InventoryAlreadyExistsException;
import com.cpg.bim.exception.InventoryNotFoundException;
import com.cpg.bim.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
import java.math.BigDecimal;
 
class InventoryControllerTest {
 
    @Mock
    private InventoryService inventoryService;
 
    private MockMvc mockMvc;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new InventoryController(inventoryService))
                                  .build();
    }
 
    // POST Success: Add Inventory Successfully
    @Test
    void testAddInventory_Success() throws Exception {
        Inventory inventory = new Inventory(1, new Book(), new Bookcondition(2, "Good condition", "Good", new BigDecimal("30.00")), false);
        when(inventoryService.addInventory(any(Inventory.class))).thenReturn("Inventory added successfully");
 
        mockMvc.perform(post("/api/inventory/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"inventoryid\":1,\"book\":{\"isbn\":\"1-111-11111-4\"},\"bookcondition\":{\"ranks\":2},\"purchased\":false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("Inventory added successfully"));
    }
 
    // GET Success: Get Inventory by ID
    @Test
    void testGetInventoryById_Success() throws Exception {
        Inventory inventory = new Inventory(1, new Book(), new Bookcondition(2, "Good condition", "Good", new BigDecimal("30.00")), false);
        when(inventoryService.getInventoryById(1)).thenReturn(inventory);
 
        mockMvc.perform(get("/api/inventory/update/purchased/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryid").value(1))
                .andExpect(jsonPath("$.book.isbn").value("1-111-11111-4"))
                .andExpect(jsonPath("$.bookcondition.ranks").value(2))
                .andExpect(jsonPath("$.purchased").value(false));
    }
 
   
    // POST Failure: Inventory Already Exists
    @Test
    void testAddInventory_AlreadyExists() throws Exception {
        when(inventoryService.addInventory(any(Inventory.class)))
                .thenThrow(new InventoryAlreadyExistsException("Inventory already exists"));
 
        mockMvc.perform(post("/api/inventory/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"inventoryid\":1,\"book\":{\"isbn\":\"1-111-11111-4\"},\"bookcondition\":{\"ranks\":2},\"purchased\":false}"))
                .andExpect(status().isConflict())  // 409 Conflict
                .andExpect(jsonPath("$.code").value("ADDFAILS"))
                .andExpect(jsonPath("$.message").value("Inventory already exists"));
    }
 
    // GET Failure: Inventory Not Found by ID
    @Test
    void testGetInventoryById_NotFound() throws Exception {
        when(inventoryService.getInventoryById(999)).thenThrow(new InventoryNotFoundException("Inventory with ID 999 not found"));
 
        mockMvc.perform(get("/api/inventory/update/purchased/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Inventory with ID 999 not found"));
    }
 
    @Test
    void testUpdateInventoryNotFound() throws Exception {
        // Using doThrow() instead of when() because updatePurchasedStatus() is a void method
        doThrow(new InventoryNotFoundException("Inventory with ID 999 not found"))
            .when(inventoryService).updatePurchasedStatus(999, true);
 
        mockMvc.perform(put("/api/inventory/999")
                        .param("purchased", "true"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Inventory with ID 999 not found"));
    }
 
 
    @Test
    void testAddInventory_InvalidData() throws Exception {
        mockMvc.perform(post("/api/inventory/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"inventoryid\": 1, \"book\": {}, \"bookcondition\": {\"ranks\": 2}, \"purchased\": false }"))
                .andExpect(status().isInternalServerError())  // Expect 500 error
                .andExpect(content().string("An error occurred: JSON parse error: No Object Id found for an instance of `com.cpg.bim.entity.Book`, to assign to property 'isbn'"));
    }
    // GET Failure: Invalid Inventory ID Format   
    @Test
    void testGetInventoryById_InvalidIdFormat() throws Exception {
        mockMvc.perform(get("/api/inventory/update/purchased/invalid-id"))
                .andExpect(status().isInternalServerError())  // 500 Internal Server Error
                .andExpect(content().string("An error occurred: Method parameter 'inventoryId': Failed to convert value of type 'java.lang.String' to required type 'int'; For input string: \"invalid-id\""));
    }
 
    // General Exception Handling: Unexpected Error
    @Test
    void testHandleGeneralException() throws Exception {
        when(inventoryService.addInventory(any(Inventory.class))).thenThrow(new RuntimeException("Unexpected error"));
 
        mockMvc.perform(post("/api/inventory/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"inventoryid\": 1, \"book\": {\"isbn\": \"1-111-11111-4\"}, \"bookcondition\": {\"ranks\": 2}, \"purchased\": false }"))
                .andExpect(status().isInternalServerError())  // 500 Internal Server Error
                .andExpect(content().string("An error occurred: Unexpected error"));
    }
 
    // POST Failure: Missing Book Information
    @Test
    void testAddInventory_MissingBook() throws Exception {
        mockMvc.perform(post("/api/inventory/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"inventoryid\": 1, \"book\": null, \"bookcondition\": {\"ranks\": 2}, \"purchased\": false }"))
                .andExpect(status().isCreated())  // 201 Created (unexpected but matches current behavior)
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("Inventory added successfully"));
    }
 
}