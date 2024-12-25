package com.cpg.bim.controller;

import java.nio.file.FileSystemAlreadyExistsException;
import java.util.List;

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

import com.cpg.bim.dto.AuthorDTO;
import com.cpg.bim.entity.Author;
import com.cpg.bim.exception.AuthorAlreadyExistsException;
import com.cpg.bim.exception.AuthorExceptionResponse;
import com.cpg.bim.exception.AuthorNotFoundException;
import com.cpg.bim.service.AuthorService;
import com.cpg.bim.service.BookService;

@RestController
@RequestMapping("/api/author")
@CrossOrigin(origins= {"http://localhost:4200"})
public class AuthorController {
 
    @Autowired
    private AuthorService authorService;
 
    @Autowired
    private BookService bookService;  // Add BookService to handle book-related logic
 
    // POST: Create new author
    @PostMapping("/post")
    public ResponseEntity<AuthorExceptionResponse> addAuthor(@RequestBody AuthorDTO authorDTO) {
    	try {
            boolean isAdded = authorService.saveAuthor(authorDTO);  // If successful, will return true
            return new ResponseEntity<>(new AuthorExceptionResponse("POSTSUCCESS", "Author Added Successfully"),HttpStatus.CREATED);  // State added successfully, return 201 status
        } catch (AuthorAlreadyExistsException ex) {
            // If the state already exists, handle the error
            return new ResponseEntity<>(new AuthorExceptionResponse("ADDFAILS", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // GET: Get author by ID
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Integer authorId) {
        AuthorDTO authorDTO = authorService.getAuthorById(authorId);
        if (authorDTO != null) {
            return new ResponseEntity<>(authorDTO, HttpStatus.OK);
        } 
        throw new AuthorNotFoundException("Author doesn't exist with the given ID");
    }

    
    // GET: Get authors by first name
    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<List<AuthorDTO>> getAuthorsByFirstName(@PathVariable String firstName) {
        List<AuthorDTO> authorDTOs = authorService.findAuthorsByFirstName(firstName);
        if (authorDTOs != null && !authorDTOs.isEmpty()) {
            return new ResponseEntity<>(authorDTOs, HttpStatus.OK);
        }
        throw new AuthorNotFoundException("Authors don't exist with the given first name: " + firstName);
    }


    // GET: Get authors by last name
    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<List<AuthorDTO>> getAuthorsByLastName(@PathVariable String lastName) {
        List<AuthorDTO> authorDTOs = authorService.findAuthorsByLastName(lastName);
        if (authorDTOs != null && !authorDTOs.isEmpty()) {
            return new ResponseEntity<>(authorDTOs, HttpStatus.OK);
        }
        throw new AuthorNotFoundException("Authors don't exist with the given last name: " + lastName);

    }

    //GET: Get all books for a specific author by their ID
    @GetMapping("/books/{authorId}")
	public ResponseEntity<List<Object[]>> getAllBooks(@PathVariable("authorId") Integer authorId) {
		List<Object[]> getAllBooks= authorService.getAllbooksByAuthorId(authorId);
		if(!getAllBooks.isEmpty()) {
			return new ResponseEntity <List<Object[]>>(getAllBooks,HttpStatus.OK);
		}
		throw new AuthorNotFoundException("Books Not Found with the given ID");
	}



    // PUT: Update author first name
    @PutMapping("/update/firstname/{authorId}")
    public ResponseEntity<String> updateAuthorFirstName(@PathVariable("authorId") Integer authorId, @RequestBody String firstName) {
         
        return new ResponseEntity<>(authorService.updateAuthorFirstName(authorId, firstName), HttpStatus.OK);
    }

    // PUT: Update author last name
    @PutMapping("/update/lastname/{authorId}")
    public ResponseEntity<String> updateAuthorLastName(@PathVariable("authorId") Integer authorId, @RequestBody String lastName) {
        
            return new ResponseEntity<>(authorService.updateAuthorLastName(authorId, lastName), HttpStatus.OK);
        
    }
}