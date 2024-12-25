package com.cpg.bim.service.test;
 
import com.cpg.bim.entity.BookReview;
import com.cpg.bim.entity.Reviewer;
import com.cpg.bim.exception.ReviewAlreadyExistsException;
import com.cpg.bim.repository.BookReviewRepository;
import com.cpg.bim.repository.ReviewerRepository;
import com.cpg.bim.repository.UserRepository;
import com.cpg.bim.service.BookReviewService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
 
import java.util.*;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
class BookReviewServiceTest {
 
    @Mock
    private BookReviewRepository bookReviewRepository;
 
    @Mock
    private ReviewerRepository reviewerRepository;
 
    @Mock
    private UserRepository usersRepository;
 
    @InjectMocks
    private BookReviewService bookReviewService;
 
    private BookReview bookReview;
    private Reviewer reviewer;
 
    @BeforeEach
    void setUp() {
        bookReview = new BookReview("123456789", 1, 5, "Great Book");
        reviewer = new Reviewer(1, "John Doe", "john@example.com");
    }
 
    // Test case 1: Add new book review - when review already exists
    @Test
    void testAddBookReview_ReviewAlreadyExists() {
        // Arrange
        when(bookReviewRepository.existsByIsbnAndReviewerid(bookReview.getIsbn(), bookReview.getReviewerid())).thenReturn(true);
 
        // Act
        Map<String, String> response = bookReviewService.addBookReview(bookReview);
 
        // Assert
        assertEquals("ADDFAILS", response.get("code"));
        assertEquals("Book Reviewer already exists", response.get("message"));
    }
 
    // Test case 2: Add new book review - when review does not exist
    @Test
    void testAddBookReview_Success() {
        // Arrange
        when(bookReviewRepository.existsByIsbnAndReviewerid(bookReview.getIsbn(), bookReview.getReviewerid())).thenReturn(false);
        when(bookReviewRepository.save(bookReview)).thenReturn(bookReview);
 
        // Act
        Map<String, String> response = bookReviewService.addBookReview(bookReview);
 
        // Assert
        assertEquals("POSTSUCCESS", response.get("code"));
        assertEquals("Book Reviewer added successfully", response.get("message"));
    }
 
    // Test case 3: Get all reviewers by ISBN
    @Test
    void testGetAllReviewersByIsbn_Success() {
        // Arrange
        List<Integer> reviewerIds = Arrays.asList(1, 2, 3);
        when(bookReviewRepository.findReviewerIdByIsbn(bookReview.getIsbn())).thenReturn(reviewerIds);
        when(reviewerRepository.findAllById(reviewerIds)).thenReturn(Arrays.asList(reviewer));
 
        // Act
        ResponseEntity<?> response = bookReviewService.getAllReviewersByIsbn(bookReview.getIsbn());
 
        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((List<?>) response.getBody()).size() > 0);
    }
 
    @Test
    void testGetAllReviewersByIsbn_NoReviewers() {
        // Arrange
        List<Integer> reviewerIds = Collections.emptyList();
        when(bookReviewRepository.findReviewerIdByIsbn(bookReview.getIsbn())).thenReturn(reviewerIds);
 
        // Act
        ResponseEntity<?> response = bookReviewService.getAllReviewersByIsbn(bookReview.getIsbn());
 
        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((List<?>) response.getBody()).isEmpty());
    }
 
    // Test case 4: Update rating - when review exists
    @Test
    void testUpdateRating_Success() {
        // Arrange
        int newRating = 4;
        when(bookReviewRepository.findByIsbnAndReviewerid(bookReview.getIsbn(), bookReview.getReviewerid())).thenReturn(Optional.of(bookReview));
 
        // Act
        Map<String, String> response = bookReviewService.updateRating(bookReview.getIsbn(), newRating, bookReview.getReviewerid());
 
        // Assert
        assertNull(response);  // Since no response is returned, it should be null
        assertEquals(newRating, bookReview.getRating());
        verify(bookReviewRepository, times(1)).save(bookReview);
    }
 
    // Test case 5: Update rating - when review does not exist
    @Test
    void testUpdateRating_ReviewNotFound() {
        // Arrange
        int newRating = 4;
        when(bookReviewRepository.findByIsbnAndReviewerid(bookReview.getIsbn(), bookReview.getReviewerid())).thenReturn(Optional.empty());
 
        // Act & Assert
        assertThrows(ReviewAlreadyExistsException.class, () -> bookReviewService.updateRating(bookReview.getIsbn(), newRating, bookReview.getReviewerid()));
    }
 
    // Test case 6: Update comments - when review exists
    @Test
    void testUpdateComments_Success() {
        // Arrange
        String newComments = "Amazing read!";
        when(bookReviewRepository.findByIsbnAndReviewerid(bookReview.getIsbn(), bookReview.getReviewerid())).thenReturn(Optional.of(bookReview));
 
        // Act
        Map<String, String> response = bookReviewService.updateComments(bookReview.getIsbn(), newComments, bookReview.getReviewerid());
 
        // Assert
        assertNull(response);  // Since no response is returned, it should be null
        assertEquals(newComments, bookReview.getComments());
        verify(bookReviewRepository, times(1)).save(bookReview);
    }
 
    // Test case 7: Update comments - when review does not exist
    @Test
    void testUpdateComments_ReviewNotFound() {
        // Arrange
        String newComments = "Amazing read!";
        when(bookReviewRepository.findByIsbnAndReviewerid(bookReview.getIsbn(), bookReview.getReviewerid())).thenReturn(Optional.empty());
 
        // Act & Assert
        assertThrows(ReviewAlreadyExistsException.class, () -> bookReviewService.updateComments(bookReview.getIsbn(), newComments, bookReview.getReviewerid()));
    }
}