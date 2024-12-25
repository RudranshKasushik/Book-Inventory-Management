package com.cpg.bim.controller.test;
import com.cpg.bim.controller.BookConditionController;
import com.cpg.bim.entity.Bookcondition;
import com.cpg.bim.exception.BookconditionAlreadyExistsException;
import com.cpg.bim.exception.BookconditionNotFoundException;
import com.cpg.bim.service.BookConditionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
 
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
class BookconditionControllerTest {
 
    @Mock
    private BookConditionService bookconditionService;
 
    @InjectMocks
    private BookConditionController bookconditionController;
 
    private MockMvc mockMvc;
    private Bookcondition bookcondition;
 
    @BeforeEach
    void setUp() {
        // Initialize the mocks and the MockMvc instance
        MockitoAnnotations.openMocks(this);  
        mockMvc = MockMvcBuilders.standaloneSetup(bookconditionController).build();
 
        // Initialize the Bookcondition object for testing
        bookcondition = new Bookcondition();
        bookcondition.setRanks(1);
        bookcondition.setPrice(new BigDecimal("50.00"));
        bookcondition.setDescription("Good condition");
        bookcondition.setFulldescription("Good condition with minor scratches.");
    }
 
    @Test
    void testAddBookcondition_Success() throws Exception {
        // Mock service to do nothing when adding a book condition
        doNothing().when(bookconditionService).addBookcondition(any(Bookcondition.class));
 
        mockMvc.perform(post("/api/bookcondition/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"ranks\": 1, \"price\": 50.00, \"description\": \"Good condition\", \"fulldescription\": \"Good condition with minor scratches.\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("POSTSUCCESS"))
                .andExpect(jsonPath("$.message").value("Book condition added successfully"));
    }
 
    @Test
    void testAddBookcondition_AlreadyExists() throws Exception {
        // Mock service to throw BookconditionAlreadyExistsException
        doThrow(new BookconditionAlreadyExistsException("Book condition already exists"))
                .when(bookconditionService).addBookcondition(any(Bookcondition.class));
 
        mockMvc.perform(post("/api/bookcondition/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"ranks\": 1, \"price\": 50.00, \"description\": \"Good condition\", \"fulldescription\": \"Good condition with minor scratches.\" }"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("ADDFAILS"))
                .andExpect(jsonPath("$.message").value("Book condition already exists"));
    }
 
    @Test
    void testGetBookconditionForPriceUpdate_Success() throws Exception {
        // Mock service to return a Bookcondition for the given ranks
        when(bookconditionService.getBookconditionByRanks(bookcondition.getRanks()))
                .thenReturn(Optional.of(bookcondition));
 
        mockMvc.perform(get("/api/bookcondition/update/price/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ranks").value(1))
                .andExpect(jsonPath("$.price").value(50.00));
    }
 
    @Test
    void testGetBookconditionForPriceUpdate_NotFound() throws Exception {
        // Mock service to return empty Optional (not found)
        when(bookconditionService.getBookconditionByRanks(bookcondition.getRanks()))
                .thenReturn(Optional.empty());
 
        mockMvc.perform(get("/api/bookcondition/update/price/1"))
                .andExpect(status().isNoContent());
    }
 
    @Test
    void testUpdatePrice_Success() throws Exception {
        // Mock response to indicate successful price update
        Map<String, String> response = new HashMap<>();
        response.put("message", "Price updated successfully for Book condition with ranks 1");
 
        // Mock service to do nothing when updating price
        doNothing().when(bookconditionService).updatePrice(eq(1), any(BigDecimal.class));
 
        mockMvc.perform(put("/api/bookcondition/1")
                .param("price", "60.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Price updated successfully for Book condition with ranks 1"));
    }
 
    @Test
    void testUpdatePrice_NotFound() throws Exception {
        // Mock service to throw BookconditionNotFoundException when updating price
        doThrow(new BookconditionNotFoundException("Book condition not found"))
                .when(bookconditionService).updatePrice(eq(1), any(BigDecimal.class));
 
        mockMvc.perform(put("/api/bookcondition/1")
                .param("price", "60.00"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book condition with ranks 1 not found"));
    }
    @Test
    void testUpdateFullDescription_Success() throws Exception {
        // Mock service to do nothing when updating full description
        doNothing().when(bookconditionService).updateFullDescription(eq(1), any(String.class));
 
        mockMvc.perform(put("/api/bookcondition/update/fullDescription/1")
                .param("fullDescription", "Updated full description"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Full description updated successfully for Book condition with ranks 1"));
    }
    @Test
    void testUpdateFullDescription_NotFound() throws Exception {
        // Mock service to throw BookconditionNotFoundException when updating full description
        doThrow(new BookconditionNotFoundException("Book condition not found"))
                .when(bookconditionService).updateFullDescription(eq(1), any(String.class));
 
        mockMvc.perform(put("/api/bookcondition/update/fullDescription/1")
                .param("fullDescription", "Updated full description"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book condition with ranks 1 not found"));
    }
    @Test
    void testUpdateDescription_Success() throws Exception {
        // Mock service to do nothing when updating description
        doNothing().when(bookconditionService).updateDescription(eq(1), any(String.class));
 
        mockMvc.perform(put("/api/bookcondition/update/description/1")
                .param("description", "Updated description"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Description updated successfully for Book condition with ranks 1"));
    }
    @Test
    void testUpdateDescription_NotFound() throws Exception {
        // Mock service to throw BookconditionNotFoundException when updating description
        doThrow(new BookconditionNotFoundException("Book condition not found"))
                .when(bookconditionService).updateDescription(eq(1), any(String.class));
 
        mockMvc.perform(put("/api/bookcondition/update/description/1")
                .param("description", "Updated description"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book condition with ranks 1 not found"));
    }
    @Test
    void testGeneralExceptionHandling() throws Exception {
        // Mock service to throw a general exception
        doThrow(new RuntimeException("Unexpected error")).when(bookconditionService).addBookcondition(any(Bookcondition.class));
 
        mockMvc.perform(post("/api/bookcondition/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"ranks\": 1, \"price\": 50.00, \"description\": \"Good condition\", \"fulldescription\": \"Good condition with minor scratches.\" }"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred: Unexpected error"));
    }
    @Test
    void testHandleBookconditionNotFoundException() throws Exception {
        // Throw exception manually in controller
        doThrow(new BookconditionNotFoundException("Book condition with ranks 1 not found"))
                .when(bookconditionService).getBookconditionByRanks(eq(1));
 
        mockMvc.perform(get("/api/bookcondition/update/price/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book condition with ranks 1 not found"));
    }
    @Test
    void testHandleBookconditionAlreadyExistsException() throws Exception {
        // Mock service to throw BookconditionAlreadyExistsException
        doThrow(new BookconditionAlreadyExistsException("Book condition already exists"))
                .when(bookconditionService).addBookcondition(any(Bookcondition.class));
 
        mockMvc.perform(post("/api/bookcondition/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"ranks\": 1, \"price\": 50.00, \"description\": \"Good condition\", \"fulldescription\": \"Good condition with minor scratches.\" }"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("ADDFAILS"))
                .andExpect(jsonPath("$.message").value("Book condition already exists"));
    }
 
 
 
 
}