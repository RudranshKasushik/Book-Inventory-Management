package com.cpg.bim.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpg.bim.entity.Reviewer;
import com.cpg.bim.exception.ReviewAlreadyExistsException;
import com.cpg.bim.exception.ReviewerNotFoundException;
import com.cpg.bim.repository.ReviewerRepository;

@Service
public class ReviewerService {

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Transactional
    public boolean addReviewer(Reviewer reviewer) {
        Optional<Reviewer> r = reviewerRepository.findById(reviewer.getReviewerId());
        if (r.isPresent()) {
            throw new ReviewAlreadyExistsException("Reviewer already exists with ID: " + reviewer.getReviewerId());
        }
        reviewerRepository.save(reviewer);
        return true;
    }

    @Transactional(readOnly = true) 
    public Reviewer getReviewerById(Integer reviewerId)throws ReviewerNotFoundException {
        return reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFoundException("Reviewer not found " + reviewerId));
    }

    @Transactional
    public Reviewer updateByNameReviewerId(Integer reviewerId, String name) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFoundException("Reviewer not found " + reviewerId));
        reviewer.setName(name);
        return reviewerRepository.save(reviewer);
    }

    @Transactional
    public Reviewer updateEmployeeByReviewerId(Integer reviewerId, String employedby) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFoundException("Reviewer not found " + reviewerId));
        reviewer.setEmployedby(employedby);
        return reviewerRepository.save(reviewer);
    }
}
