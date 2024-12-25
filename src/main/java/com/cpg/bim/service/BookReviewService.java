package com.cpg.bim.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cpg.bim.entity.BookReview;
import com.cpg.bim.entity.Reviewer;
import com.cpg.bim.exception.ReviewAlreadyExistsException;
import com.cpg.bim.exception.ReviewerNotFoundException;
import com.cpg.bim.repository.BookReviewRepository;
import com.cpg.bim.repository.ReviewerRepository;
import com.cpg.bim.repository.UserRepository;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class BookReviewService
{
	@Autowired
	BookReviewRepository bookreviewRepository;
	@Autowired
	ReviewerRepository reviewerRepository;
	@Autowired
	UserRepository userRepository;
	// 1. Add new book review object
	public 	Map<String,String> addBookReview(BookReview bookReview) {
            if (bookreviewRepository.existsByIsbnAndReviewerid(bookReview.getIsbn(), bookReview.getReviewerid())) {
                // Return failure response if the review already exists
                Map<String, String> response = new HashMap<>();
                response.put("code", "ADDFAILS");
                response.put("message", "Book Reviewer already exists");
                return response;
            }
            bookreviewRepository.save(bookReview);
            Map<String, String> response = new HashMap<>();
            response.put("code", "POSTSUCCESS");
            response.put("message", "Book Reviewer added successfully");
            return response;
    }
	// 2. To get list of all reviewers with given isbn
	@Transactional
	public ResponseEntity<?> getAllReviewersByIsbn(String isbn) {
		List<Integer> rlist = bookreviewRepository.findReviewerIdByIsbn(isbn);
		List<Reviewer> reviewerList = reviewerRepository.findAllById(rlist);
		//reviewerRepository.findAllById(null);
	    // Fetch all reviews for the given ISBN using the native query
	  /*  List<BookReview> reviews = bookreviewRepository.findByIsbnNative(isbn);
	    System.out.println("Fetched reviews: " + reviews.size());
	    if (reviews.isEmpty()) {
	        throw new ReviewAlreadyExistsException("No reviews found for ISBN: " + isbn);
	    }*/
	    // Extract reviewer details and return
	   /* List<Reviewer> reviewerResponses = new ArrayList<>();
	    for (BookReview review : reviews) {
	        Reviewer reviewer = reviewerRepository.findById(review.getReviewerid()).orElse(null);
	        if (reviewer != null) {
	            reviewerResponses.add(reviewer);
	        }
	    }
	    if (reviewerResponses.isEmpty()) {
	        throw new ReviewAlreadyExistsException("No reviewers found for ISBN: " + isbn);
	    }*/
	   // return ResponseEntity.ok(reviewerResponses);
	    return ResponseEntity.ok(reviewerList);
	}
    // 3. Update rating by ISBN and reviewerid
    public Map<String,String> updateRating(String isbn, int rating, int reviewerid) {
        Optional<BookReview> review = bookreviewRepository.findByIsbnAndReviewerid(isbn, reviewerid);
        // If review does not exist, throw a ResourceNotFoundException
        if (!review.isPresent()) {
            throw new ReviewAlreadyExistsException("Review not found for ISBN: " + isbn + " and reviewer ID: " + reviewerid);
        }
        BookReview bookReview = review.get();
        bookReview.setRating(rating);
        bookreviewRepository.save(bookReview);
        return null;
    }
    // 4. Update comments with given ISBN and reviewerid
    public Map<String,String> updateComments(String isbn, String comments, int reviewerid) {
        Optional<BookReview> review = bookreviewRepository.findByIsbnAndReviewerid(isbn, reviewerid);
        // If review does not exist, throw a ResourceNotFoundException
        if (!review.isPresent()) {
            throw new ReviewAlreadyExistsException("Review not found for ISBN: " + isbn + " and reviewer ID: " + reviewerid);
        }
        BookReview bookReview = review.get();
        bookReview.setComments(comments.trim());
        bookreviewRepository.save(bookReview);
		return null;
    }
    
    public List<BookReview> getReviewsByIsbn(String isbn) {
        return bookreviewRepository.findByIsbn(isbn);
    }
}