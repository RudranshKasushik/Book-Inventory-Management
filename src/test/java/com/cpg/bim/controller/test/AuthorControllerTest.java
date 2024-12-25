package com.cpg.bim.controller.test;

import com.cpg.bim.controller.AuthorController;
import com.cpg.bim.dto.AuthorDTO;
import com.cpg.bim.entity.Author;
import com.cpg.bim.exception.AuthorAlreadyExistsException;
import com.cpg.bim.exception.AuthorNotFoundException;
import com.cpg.bim.service.AuthorService;
import com.cpg.bim.service.BookService;
import com.cpg.bim.exception.AuthorExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test addAuthor - Success scenario
    @Test
    void testAddAuthorSuccess() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setFirstname("John");
        authorDTO.setLastname("Doe");

        when(authorService.saveAuthor(any(AuthorDTO.class))).thenReturn(true);

        ResponseEntity<AuthorExceptionResponse> response = authorController.addAuthor(authorDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("POSTSUCCESS", response.getBody().getCode());
        assertEquals("Author Added Successfully", response.getBody().getMessage());
    }

    // Test addAuthor - Failure scenario (Author already exists)
    @Test
    void testAddAuthorFailure() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setFirstname("John");
        authorDTO.setLastname("Doe");

        when(authorService.saveAuthor(any(AuthorDTO.class))).thenThrow(AuthorAlreadyExistsException.class);

        ResponseEntity<AuthorExceptionResponse> response = authorController.addAuthor(authorDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ADDFAILS", response.getBody().getCode());
    }

    // Test getAuthorById - Success scenario
    @Test
    void testGetAuthorByIdSuccess() {
        Integer authorId = 1;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setFirstname("John");
        authorDTO.setLastname("Doe");

        when(authorService.getAuthorById(authorId)).thenReturn(authorDTO);

        ResponseEntity<AuthorDTO> response = authorController.getAuthorById(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authorDTO, response.getBody());
    }

    // Test getAuthorById - Author not found
    @Test
    void testGetAuthorByIdNotFound() {
        Integer authorId = 1;

        when(authorService.getAuthorById(authorId)).thenReturn(null);

        try {
            authorController.getAuthorById(authorId);
            fail("Expected AuthorNotFoundException");
        } catch (AuthorNotFoundException ex) {
            assertEquals("Author doesn't exist with the given ID", ex.getMessage());
        }
    }

    // Test getAuthorsByFirstName - Success scenario
    @Test
    void testGetAuthorsByFirstNameSuccess() {
        String firstName = "John";
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setFirstname("John");
        authorDTO.setLastname("Doe");
        List<AuthorDTO> authorDTOs = Arrays.asList(authorDTO);

        when(authorService.findAuthorsByFirstName(firstName)).thenReturn(authorDTOs);

        ResponseEntity<List<AuthorDTO>> response = authorController.getAuthorsByFirstName(firstName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authorDTOs, response.getBody());
    }

    // Test getAuthorsByFirstName - Authors not found
    @Test
    void testGetAuthorsByFirstNameNotFound() {
        String firstName = "John";

        when(authorService.findAuthorsByFirstName(firstName)).thenReturn(null);

        try {
            authorController.getAuthorsByFirstName(firstName);
            fail("Expected AuthorNotFoundException");
        } catch (AuthorNotFoundException ex) {
            assertEquals("Authors don't exist with the given first name: John", ex.getMessage());
        }
    }

    // Test updateAuthorFirstName - Success scenario
    @Test
    void testUpdateAuthorFirstNameSuccess() {
        Integer authorId = 1;
        String firstName = "Jane";

        when(authorService.updateAuthorFirstName(authorId, firstName)).thenReturn("First name updated successfully");

        ResponseEntity<String> response = authorController.updateAuthorFirstName(authorId, firstName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("First name updated successfully", response.getBody());
    }

    // Test updateAuthorLastName - Success scenario
    @Test
    void testUpdateAuthorLastNameSuccess() {
        Integer authorId = 1;
        String lastName = "Smith";

        when(authorService.updateAuthorLastName(authorId, lastName)).thenReturn("Last name updated successfully");

        ResponseEntity<String> response = authorController.updateAuthorLastName(authorId, lastName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Last name updated successfully", response.getBody());
    }

    // Test getAllBooks - Success scenario
    @Test
    void testGetAllBooksSuccess() {
        Integer authorId = 1;
        List<Object[]> books = Arrays.asList(new Object[]{"Book 1"}, new Object[]{"Book 2"});

        when(authorService.getAllbooksByAuthorId(authorId)).thenReturn(books);

        ResponseEntity<List<Object[]>> response = authorController.getAllBooks(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    // Test getAllBooks - No books found
    @Test
    void testGetAllBooksNotFound() {
        Integer authorId = 1;

        when(authorService.getAllbooksByAuthorId(authorId)).thenReturn(List.of());

        try {
            authorController.getAllBooks(authorId);
            fail("Expected AuthorNotFoundException");
        } catch (AuthorNotFoundException ex) {
            assertEquals("Books Not Found with the given ID", ex.getMessage());
        }
    }
}
