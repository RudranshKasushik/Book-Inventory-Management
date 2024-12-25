package com.cpg.bim.controller.test;

import com.cpg.bim.controller.ReviewerController;
import com.cpg.bim.entity.Reviewer;
import com.cpg.bim.exception.ReviewerNotFoundException;
import com.cpg.bim.service.ReviewerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
 
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@WebMvcTest(ReviewerController.class)
@ExtendWith(MockitoExtension.class)
public class ReviewerControllerTest {
 
    @Autowired
    MockMvc mockMvc;
 
    @MockBean
    ReviewerService reviewerService;
 
    ObjectMapper objectMapper = new ObjectMapper();
 
    Reviewer reviewer = new Reviewer(1, "John Doe", "Company A");
 
    @Test
    public void testPostMapping_addReviewer() throws Exception {
        // Test for successful addition of reviewer
        when(reviewerService.addReviewer(reviewer)).thenReturn(true);
 
        mockMvc.perform(post("/api/reviewer/post")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(reviewer)))
                .andExpect(status().isCreated());
    }
   
   
    @Test
    public void testPutMapping_updateReviewerName() throws Exception {
        // Test for successful name update
        when(reviewerService.updateByNameReviewerId(1, "Jane Doe")).thenReturn(reviewer);
 
        mockMvc.perform(put("/api/reviewer/name/{reviewerId}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new Reviewer(1, "Jane Doe", "Company A"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employedby").value("Company A"));
    }
 
    @Test
    public void testPutMapping_updateReviewerName_NotFound() throws Exception {
        // Test when reviewer is not found for name update
        when(reviewerService.updateByNameReviewerId(1, "Jane Doe")).thenThrow(new ReviewerNotFoundException("Reviewer not found"));
 
        mockMvc.perform(put("/api/reviewer/name/{reviewerId}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new Reviewer(1, "Jane Doe", "Company A"))))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reviewer not found with given Id"));
    }
 
    @Test
    public void testPutMapping_updateReviewerEmploymentStatus() throws Exception {
        // Test for successful employment status update
        when(reviewerService.updateEmployeeByReviewerId(1, "Company B")).thenReturn(reviewer);
 
        mockMvc.perform(put("/api/reviewer/{reviewerId}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new Reviewer(1, "John Doe", "Company B"))));
                
    }
 
    @Test
    public void testPutMapping_updateReviewerEmploymentStatus_NotFound() throws Exception {
        // Test when reviewer is not found for employment status update
        when(reviewerService.updateEmployeeByReviewerId(1, "Company B")).thenThrow(new ReviewerNotFoundException("Reviewer not found"));
 
        mockMvc.perform(put("/api/reviewer/{reviewerId}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new Reviewer(1, "John Doe", "Company B"))));
    }
}
 