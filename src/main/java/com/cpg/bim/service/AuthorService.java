package com.cpg.bim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpg.bim.dto.AuthorDTO;
import com.cpg.bim.entity.Author;
import com.cpg.bim.exception.AuthorAlreadyExistsException;
import com.cpg.bim.exception.AuthorNotFoundException;
import com.cpg.bim.repository.AuthorRepository;
import com.cpg.bim.repository.BookAuthorRepository;
import com.cpg.bim.repository.BookRepository;

@Service
public class AuthorService {
 
    @Autowired
    private AuthorRepository authorRepository;
 
    @Autowired
    private BookRepository bookRepository;  // Inject BookRepository to fetch books
 
    @Autowired
    BookAuthorRepository bookAuthorRepository;
    // Add Author (POST)
    @Transactional
    public boolean saveAuthor(AuthorDTO authorDTO) {
        // Check if author with the same ID already exists
        if (authorDTO.getId() != 0) { // Assume 0 indicates a new author
            Optional<Author> existingAuthorById = authorRepository.findById(authorDTO.getId());
            if (existingAuthorById.isPresent()) {
                throw new AuthorAlreadyExistsException(
                    "Author with ID " + authorDTO.getId() + " already exists."
                );
            }
        }

        // Convert DTO to entity
        Author newAuthor = new Author();
        newAuthor.setId(authorDTO.getId()); // Include the ID from DTO
        newAuthor.setFirstname(authorDTO.getFirstname());
        newAuthor.setLastname(authorDTO.getLastname());
        newAuthor.setPhoto(authorDTO.getPhoto());

        // Save the new author entity
        authorRepository.save(newAuthor);
        return true;
    }



    @Transactional(readOnly = true)
    public AuthorDTO getAuthorById(Integer authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isPresent()) {
            Author authorEntity = author.get();
            return new AuthorDTO(authorEntity);
        }
        return null;
    }

    // Find Authors by First Name and return as a list of DTOs
    @Transactional(readOnly = true)
    public List<AuthorDTO> findAuthorsByFirstName(String firstName) {
        List<Author> authors = authorRepository.findByFirstname(firstName);
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        for (Author author : authors) {
            authorDTOs.add(new AuthorDTO(author));
        }
        return authorDTOs;
    }

    // Find Authors by Last Name and return as a list of DTOs
    @Transactional(readOnly = true)
    public List<AuthorDTO> findAuthorsByLastName(String lastName) {
        List<Author> authors = authorRepository.findByLastname(lastName);
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        for (Author author : authors) {
            authorDTOs.add(new AuthorDTO(author));
        }
        return authorDTOs;
    }

    // Find Books by Author ID and return as a list of DTOs
    @Transactional
    public List<Object[]> getAllbooksByAuthorId(Integer authorId) {	
    	List<Object[]> allBooks = authorRepository.findBooksByAuthorid(authorId);
    	  if(allBooks!=null) {
    		  return allBooks;
    	  }
    	  
    	  return null;
    }

    // Update Author's First Name (PUT)
    @Transactional
    public String updateAuthorFirstName(Integer authorId, String firstname) {
        // Find author by ID and update their first name
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author doesn't exist"));
        author.setFirstname(firstname);
        return "Author first name updated successfully";
    }
      
    //Update Author's Last Name (PUT)
    @Transactional
    public String updateAuthorLastName(Integer authorId, String lastname) {
        // Find author by ID and update their last name
        Author author = authorRepository.findById(authorId) 
        		.orElseThrow(() -> new AuthorNotFoundException("Author doesn't exist"));
            author.setLastname(lastname);
            return "Author last name updated successfully";
    }
}