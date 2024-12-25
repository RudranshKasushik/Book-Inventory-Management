package com.cpg.bim.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cpg.bim.entity.BookReview;
import com.cpg.bim.entity.Reviewer;
import com.cpg.bim.service.BookReviewService;
import java.util.List;
import java.util.Map; 
@RestController
@RequestMapping("/api/bookreview")
@CrossOrigin(origins = "http://localhost:4200")
public class BookReviewController
{
	@Autowired
	private BookReviewService bookreviewService;
	//1. Add a new book review (POST request)
	@PostMapping("/post")
	public Map<String,String> addBookReview(@RequestBody BookReview bookreview) {
		return (Map<String, String>) bookreviewService.addBookReview(bookreview);
	}
	//2. Get all reviewers with a given ISBN number (GET request)
	@GetMapping("/{isbn}")
    public ResponseEntity<?> getAllReviewersByIsbn(@PathVariable String isbn) {
		List<Reviewer> rlist = (List<Reviewer>)bookreviewService.getAllReviewersByIsbn(isbn).getBody();
		for(Reviewer r : rlist)
			System.out.println(r.getReviewerId()+"   "+r.getName());
       return bookreviewService.getAllReviewersByIsbn(isbn);
    }
	// Update rating with given ISBN (PUT request)
	@PutMapping("/update/rating/{isbn}")
	public Map<String,String> updateRating(@PathVariable String isbn, @RequestBody int rating, @RequestParam int reviewerid) {
		  return (Map<String, String>) bookreviewService.updateRating(isbn, rating, reviewerid);
	}
	// Update comments with given ISBN (PUT request)
	@PutMapping("/update/comments/{isbn}")
	public Map<String,String> updateComments(@PathVariable String isbn, @RequestBody String comments, @RequestParam int reviewerid) {
		  return (Map<String, String>) bookreviewService.updateComments(isbn, comments, reviewerid);
	}
	
	
	@GetMapping("/reviews/{isbn}")
    public ResponseEntity<List<BookReview>> getReviewsByIsbn(@PathVariable String isbn) {
        List<BookReview> reviews = bookreviewService.getReviewsByIsbn(isbn);
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();  // No content if no reviews found
        }
        return ResponseEntity.ok(reviews);  // Return reviews with a 200 OK response
    }
}