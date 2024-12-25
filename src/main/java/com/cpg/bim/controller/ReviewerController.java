package com.cpg.bim.controller;

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

import com.cpg.bim.entity.Reviewer;
import com.cpg.bim.exception.ReviewAlreadyExistsException;
import com.cpg.bim.exception.ReviewerNotFoundException;
import com.cpg.bim.service.ReviewerService;
import com.cpg.bim.exception.ReviewerExceptionResponse;

@RestController
@RequestMapping("/api/reviewer")
@CrossOrigin(origins= {"http://localhost:4200"})
public class ReviewerController {

    @Autowired
    private ReviewerService reviewerService;

    @PostMapping("/post")
    public ResponseEntity<ReviewerExceptionResponse> addReviewer(@RequestBody Reviewer reviewer) {

        try {
            boolean isAdded = reviewerService.addReviewer(reviewer);  // If successful, will return true
            return new ResponseEntity<>(new ReviewerExceptionResponse("POSTSUCCESS", "Reviewer Added Successfully"),HttpStatus.CREATED);  // State added successfully, return 201 status
        } catch (ReviewAlreadyExistsException ex) {
            // If the state already exists, handle the error
            return new ResponseEntity<>(new ReviewerExceptionResponse("ADDFAILS", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Get reviewer by ID
    @GetMapping("{reviewerId}")
    public ResponseEntity<?> getReviewer(@PathVariable("reviewerId") Integer reviewerId) {
        try {
            Reviewer reviewer = reviewerService.getReviewerById(reviewerId);
            return new ResponseEntity<>(reviewer, HttpStatus.OK);
        } catch (ReviewerNotFoundException e) {
            return new ResponseEntity<>("Reviewer Not found",HttpStatus.NOT_FOUND);
        }
    }

    // Update reviewer name by ID
    @PutMapping("/name/{reviewerId}")
    public ResponseEntity<?> updateName(@PathVariable Integer reviewerId, @RequestBody Reviewer reviewer) {
        try {
            Reviewer updatedReviewer = reviewerService.updateByNameReviewerId(reviewerId, reviewer.getName());
            return new ResponseEntity<>(updatedReviewer, HttpStatus.OK); // Return 200 OK with updated reviewer
        } catch (ReviewerNotFoundException e) {
            return new ResponseEntity<>("Reviewer not found with given Id", HttpStatus.NOT_FOUND); // Return 404 NOT FOUND if reviewer does not exist
        }
    }


    // Update reviewer's employment status by ID
    @PutMapping("/employedby/{reviewerId}")
    public ResponseEntity<?> updateReviewerEmployedBy(@PathVariable Integer reviewerId, @RequestBody Reviewer reviewer) {
        try {
            Reviewer updatedReviewer = reviewerService.updateEmployeeByReviewerId(reviewerId, reviewer.getEmployedby());
            return new ResponseEntity<>(updatedReviewer, HttpStatus.OK); // Return 200 OK with the updated reviewer
        } catch (ReviewerNotFoundException e) {
            return new ResponseEntity<>("Reviewer not found with given Id", HttpStatus.NOT_FOUND); // Return 404 NOT FOUND if reviewer doesn't exist
        }
    }

    }