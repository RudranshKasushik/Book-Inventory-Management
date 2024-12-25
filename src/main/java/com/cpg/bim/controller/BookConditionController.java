package com.cpg.bim.controller;
 
import com.cpg.bim.entity.Bookcondition;
import com.cpg.bim.exception.BookconditionAlreadyExistsException;
import com.cpg.bim.exception.BookconditionNotFoundException;
import com.cpg.bim.exception.BookconditionAddedSuccessfullyException;
import com.cpg.bim.service.BookConditionService;
//import com.cpg.bim.service.BookconditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/bookcondition")
public class BookConditionController {
 
    private BookConditionService bookconditionService;
 
    @Autowired
    public BookConditionController(BookConditionService bookconditionService) {
        this.bookconditionService = bookconditionService;
    }
 
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addBookcondition(@RequestBody Bookcondition bookcondition) {
        Map<String, String> response = new HashMap<>();
        try {
            bookconditionService.addBookcondition(bookcondition);
            // Return success response with code and message
            response.put("code", "POSTSUCCESS");
            response.put("message", "Book condition added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BookconditionAlreadyExistsException e) {
            // Return failure response when the book condition already exists
            response.put("code", "ADDFAILS");
            response.put("message", "Book condition already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
 
 
    // Get a Bookcondition by ranks
    @GetMapping("/update/price/{ranks}")
    public ResponseEntity<Bookcondition> getBookconditionForPriceUpdate(@PathVariable int ranks) {
        Optional<Bookcondition> bookcondition = bookconditionService.getBookconditionByRanks(ranks);
        return bookcondition
                .map(ResponseEntity::ok) 
                .orElseGet(() -> ResponseEntity.noContent().build());  
    }
 
 
    // Update the price of a Bookcondition
    @PutMapping("{ranks}")
    public ResponseEntity<Map<String, String>> updatePrice(@PathVariable int ranks, @RequestParam BigDecimal price) {
        try {
            bookconditionService.updatePrice(ranks, price);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Price updated successfully for Book condition with ranks " + ranks);
            return ResponseEntity.status(HttpStatus.OK).body(response);  // 200 OK
        } catch (BookconditionNotFoundException e) {
            throw new BookconditionNotFoundException("Book condition with ranks " + ranks + " not found");
        }
    }
 
    // Update the full description of a Bookcondition
    @PutMapping("/update/fullDescription/{ranks}")
    public ResponseEntity<Map<String, String>> updateFullDescription(@PathVariable int ranks, @RequestParam String fullDescription) {
        try {
            bookconditionService.updateFullDescription(ranks, fullDescription);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Full description updated successfully for Book condition with ranks " + ranks);
            return ResponseEntity.status(HttpStatus.OK).body(response);  // 200 OK
        } catch (BookconditionNotFoundException e) {
            throw new BookconditionNotFoundException("Book condition with ranks " + ranks + " not found");
        }
    }
 
    // Update the description of a Bookcondition
    @PutMapping("/update/description/{ranks}")
    public ResponseEntity<Map<String, String>> updateDescription(@PathVariable int ranks, @RequestParam String description) {
        try {
            bookconditionService.updateDescription(ranks, description);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Description updated successfully for Book condition with ranks " + ranks);
            return ResponseEntity.status(HttpStatus.OK).body(response);  // 200 OK
        } catch (BookconditionNotFoundException e) {
            throw new BookconditionNotFoundException("Book condition with ranks " + ranks + " not found");
        }
    }
}