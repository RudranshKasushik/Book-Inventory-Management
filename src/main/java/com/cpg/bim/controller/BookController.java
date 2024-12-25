package com.cpg.bim.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpg.bim.dto.BookDTO;
import com.cpg.bim.dto.PublisherDTO;
import com.cpg.bim.entity.Book;
import com.cpg.bim.entity.Category;
import com.cpg.bim.entity.Publisher;
import com.cpg.bim.exception.BookNotFoundException;
import com.cpg.bim.repository.CategoryRepository;
import com.cpg.bim.repository.PublisherRepository;
import com.cpg.bim.service.BookService;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins= {"http://localhost:4200"})
public class BookController {

    @Autowired
    BookService bookServ;
    
    @Autowired
    PublisherRepository publisherRepo;
    
    @Autowired
    CategoryRepository categoryRepo;

    @PostMapping(value = "/addbook", consumes = "application/json")
    public ResponseEntity<Map<String, String>> addBook(@RequestBody BookDTO bookDTO) {
        Map<String, String> response = bookServ.addBook(bookDTO);

        // Return the response based on the result of addBook method
        if ("POSTSUCCESS".equals(response.get("code"))) {
            return ResponseEntity.ok(response); // 200 OK
        } else {
            return ResponseEntity.status(400).body(response); // 400 Bad Request
        }
    }

    @GetMapping(value = "/allbooks", produces = "application/json")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return new ResponseEntity<>(bookServ.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping(value = "/byId/{isbn}", produces = "application/json")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String isbn) {
        return new ResponseEntity<>(bookServ.getBookById(isbn), HttpStatus.OK);
    }

    @GetMapping(value = "/title/{title}", produces = "application/json")
    public ResponseEntity<BookDTO> getBookByTitle(@PathVariable String title) {
        return new ResponseEntity<>(bookServ.getBookByTitle(title), HttpStatus.OK);
    }

    @GetMapping(value = "/category/{category}", produces = "application/json")
    public ResponseEntity<List<BookDTO>> getBookByCategory(@PathVariable int category) {
        List<BookDTO> books = bookServ.getBookByCategory(category);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(value = "/publisher/{publisherid}", produces = "application/json")
    public ResponseEntity<List<BookDTO>> getBookByPublisher(@PathVariable int publisherid) {
        List<BookDTO> books = bookServ.getBookByPublisher(publisherid);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping("/updateTitle/{isbn}")
    public ResponseEntity<BookDTO> updateBookTitle(@PathVariable String isbn, @RequestBody String newTitle) {
        try {
            BookDTO updatedBook = bookServ.updateBookTitle(isbn, newTitle);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateDesc/{isbn}")
    public ResponseEntity<BookDTO> updateBookDescription(@PathVariable String isbn, @RequestBody String newDesc) {
        BookDTO updatedBook = bookServ.updatedBookDescription(isbn, newDesc);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @PutMapping("/updateCategory/{isbn}")
    public ResponseEntity<BookDTO> updateBookCategory(@PathVariable String isbn, @RequestBody int newCat) {
        try {
            // Call the service to update the book's category
            BookDTO updatedBook = bookServ.updatedBookCategory(isbn, newCat);

            // Return the updated book as a response
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            // Handle exceptions related to missing book or category
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle any other unexpected errors
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateEdition/{isbn}")
    public ResponseEntity<BookDTO> updateBookEdition(@PathVariable String isbn, @RequestBody String newEdition) {
        BookDTO updatedBook = bookServ.updatedBookEdition(isbn, newEdition);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @PutMapping("/updatePublisher/{isbn}")
    public ResponseEntity<BookDTO> updateBookPublisher(@PathVariable String isbn, @RequestBody PublisherDTO newPublisherDTO) {
        try {
            // Call the service to update the book's publisher
            BookDTO updatedBook = bookServ.updateBookPublisher(isbn, newPublisherDTO);

            // Return the updated book as a response
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            // Handle exceptions related to missing book or publisher
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle any other unexpected errors
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
