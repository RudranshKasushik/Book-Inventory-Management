package com.cpg.bim.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cpg.bim.entity.Reviewer;
import com.cpg.bim.repository.ReviewerRepository;
import com.cpg.bim.service.ReviewerService;

import java.util.Optional;
 
@ExtendWith(MockitoExtension.class)
public class ReviewerServiceTest {
 
    @Mock
    private ReviewerRepository reviewerRepository;
 
    @InjectMocks
    private ReviewerService reviewerService;
 
    Reviewer r1 = new Reviewer(1, "John Doe", "Company A");
    Reviewer r2 = new Reviewer(2, "Jane Smith", "Company B");
 
    @Test
    public void testAddReviewer() {
        // Simulate that the reviewer with ID 1 does not exist
        when(reviewerRepository.findById(r1.getReviewerId())).thenReturn(Optional.empty());
        when(reviewerRepository.save(r1)).thenReturn(r1);
 
        // Test the addReviewer method
        boolean isAdded = reviewerService.addReviewer(r1);
        assertTrue(isAdded);
 
        // Verify that the save method was called
        verify(reviewerRepository, times(1)).save(r1);
    }
 
   
 
    @Test
    public void testGetReviewerById() {
        // Simulate that reviewer with ID 1 is found in the repository
        when(reviewerRepository.findById(r1.getReviewerId())).thenReturn(Optional.of(r1));
 
        // Test the getReviewerById method
        Reviewer reviewer = reviewerService.getReviewerById(r1.getReviewerId());
        assertNotNull(reviewer);
        assertEquals(r1, reviewer);
    }
 
   
 
    @Test
    public void testUpdateByNameReviewerId() {
        // Simulate that reviewer with ID 1 is found in the repository
        when(reviewerRepository.findById(r1.getReviewerId())).thenReturn(Optional.of(r1));
        when(reviewerRepository.save(r1)).thenReturn(r1);
 
        // Test the updateByNameReviewerId method
        String newName = "Johnathan Doe";
        Reviewer updatedReviewer = reviewerService.updateByNameReviewerId(r1.getReviewerId(), newName);
        assertEquals(newName, updatedReviewer.getName());
 
        // Verify that save was called to persist the changes
        verify(reviewerRepository, times(1)).save(r1);
    }
 
   
 
    @Test
    public void testUpdateEmployeeByReviewerId() {
        // Simulate that reviewer with ID 1 is found in the repository
        when(reviewerRepository.findById(r1.getReviewerId())).thenReturn(Optional.of(r1));
        when(reviewerRepository.save(r1)).thenReturn(r1);
 
        // Test the updateEmployeeByReviewerId method
        String newEmployedBy = "Company C";
        Reviewer updatedReviewer = reviewerService.updateEmployeeByReviewerId(r1.getReviewerId(), newEmployedBy);
        assertEquals(newEmployedBy, updatedReviewer.getEmployedby());
 
        // Verify that save was called to persist the changes
        verify(reviewerRepository, times(1)).save(r1);
    }
 
  
}