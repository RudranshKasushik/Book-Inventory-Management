package com.cpg.bim.controller.test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cpg.bim.controller.BookReviewController;
import com.cpg.bim.entity.BookReview;
import com.cpg.bim.service.BookReviewService;
public class BookReviewControllerTest {
 
    private MockMvc mockMvc;
 
    @Mock
    private BookReviewService bookReviewService;
 
    @InjectMocks
    private BookReviewController bookReviewController;
 
    @BeforeEach
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);  // Initialize mocks annotated with @Mock and @InjectMocks
        // Initialize MockMvc with the controller
        mockMvc = MockMvcBuilders.standaloneSetup(bookReviewController).build();
    }
 
    // Test for Adding a New Book Review
    @Test
    public void testAddBookReview() {
        // Arrange
        BookReview bookReview = new BookReview();
        bookReview.setIsbn("978-3-16-148410-0");
        bookReview.setRating(4);
        bookReview.setComments("Good book.");
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        when(bookReviewService.addBookReview(any(BookReview.class))).thenReturn(response);
 
        // Act
        Map<String, String> responseEntity = bookReviewController.addBookReview(bookReview);
 
        // Assert
        assertEquals("success", responseEntity.get("status"));
        verify(bookReviewService, times(1)).addBookReview(any(BookReview.class));
    }
 
    // Test for Updating Rating
    @Test
    public void testUpdateRating() {
        // Arrange
        String isbn = "978-3-16-148410-0";
        int rating = 5;
        int reviewerId = 1;
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        when(bookReviewService.updateRating(isbn, rating, reviewerId)).thenReturn(response);
 
        // Act
        Map<String, String> responseEntity = bookReviewController.updateRating(isbn, rating, reviewerId);
 
        // Assert
        assertEquals("success", responseEntity.get("status"));
        verify(bookReviewService, times(1)).updateRating(isbn, rating, reviewerId);
    }
 
    // Test for Updating Comments
    @Test
    public void testUpdateComments() {
        // Arrange
        String isbn = "978-3-16-148410-0";
        String comments = "Updated comments.";
        int reviewerId = 1;
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        when(bookReviewService.updateComments(isbn, comments, reviewerId)).thenReturn(response);
 
        // Act
        Map<String, String> responseEntity = bookReviewController.updateComments(isbn, comments, reviewerId);
 
        // Assert
        assertEquals("success", responseEntity.get("status"));
        verify(bookReviewService, times(1)).updateComments(isbn, comments, reviewerId);
    }
 
    // Test for Invalid ISBN (getAllReviewersByIsbn)
    @Test
    public void testGetAllReviewersByIsbn_InvalidIsbn() throws Exception {
        // Arrange
        String isbn = "invalid-isbn";
        when(bookReviewService.getAllReviewersByIsbn(isbn)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
 
        // Act & Assert
        mockMvc.perform(get("/api/bookreview/{isbn}", isbn))
               .andExpect(status().isNotFound());
        verify(bookReviewService, times(1)).getAllReviewersByIsbn(isbn);
    }
 
    // Test for Invalid Rating (updateRating)
    @Test
    public void testUpdateRating_InvalidRating() {
        // Arrange
        String isbn = "978-3-16-148410-0";
        int invalidRating = 15;  // Assuming rating can't be above 10
        int reviewerId = 1;
 
        Map<String, String> response = new HashMap<>();
        response.put("status", "failure");
        when(bookReviewService.updateRating(isbn, invalidRating, reviewerId)).thenReturn(response);
 
        // Act
        Map<String, String> responseEntity = bookReviewController.updateRating(isbn, invalidRating, reviewerId);
 
        // Assert
        assertEquals("failure", responseEntity.get("status"));
        verify(bookReviewService, times(1)).updateRating(isbn, invalidRating, reviewerId);
    }
}