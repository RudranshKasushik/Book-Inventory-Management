package com.cpg.bim.service.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cpg.bim.dto.AuthorDTO;
import com.cpg.bim.entity.Author;
import com.cpg.bim.exception.AuthorAlreadyExistsException;
import com.cpg.bim.exception.AuthorNotFoundException;
import com.cpg.bim.repository.AuthorRepository;
import com.cpg.bim.repository.BookAuthorRepository;
import com.cpg.bim.repository.BookRepository;
import com.cpg.bim.service.AuthorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
 
    @InjectMocks
    private AuthorService authorService;
 
    @Mock
    private AuthorRepository authorRepository;
 
    @Mock
    private BookRepository bookRepository;
 
    @Mock
    private BookAuthorRepository bookAuthorRepository;
 
    private Author author;
    private AuthorDTO authorDTO;
 
    @BeforeEach
    public void setup() {
        author = new Author();
        author.setId(1);
        author.setFirstname("John");
        author.setLastname("Doe");
        author.setPhoto("photo_url");
 
        authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setFirstname("John");
        authorDTO.setLastname("Doe");
        authorDTO.setPhoto("photo_url");
    }
 
    @Test
    public void testSaveAuthor_Success() {
        // Use lenient stubbing to avoid potential conflicts
        lenient().when(authorRepository.findById(authorDTO.getId())).thenReturn(Optional.empty());
        lenient().when(authorRepository.save(Mockito.any(Author.class))).thenReturn(author);
 
        // Test method
        boolean result = authorService.saveAuthor(authorDTO);
 
        // Verify
        assertTrue(result);
        verify(authorRepository, times(1)).save(Mockito.any(Author.class));
    }
 
    @Test
    public void testSaveAuthor_AlreadyExists() {
        // Use lenient stubbing for this test as well
        lenient().when(authorRepository.findById(authorDTO.getId())).thenReturn(Optional.of(author));
 
        // Test method and assert exception
        assertThrows(AuthorAlreadyExistsException.class, () -> authorService.saveAuthor(authorDTO));
 
        // Verify
        verify(authorRepository, never()).save(Mockito.any(Author.class));
    }
 
   
 
    @Test
    public void testGetAuthorById_NotFound() {
        // Mock behavior: no author found
        lenient().when(authorRepository.findById(1)).thenReturn(Optional.empty());
 
        // Test method
        AuthorDTO result = authorService.getAuthorById(1);
 
        // Verify
        assertNull(result); // Ensure the result is null
    }
 
 
    @Test
    public void testFindAuthorsByFirstName() {
        // Lenient stubbing
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        lenient().when(authorRepository.findByFirstname("John")).thenReturn(authors);
 
        // Test method
        List<AuthorDTO> result = authorService.findAuthorsByFirstName("John");
 
        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstname());
        verify(authorRepository, times(1)).findByFirstname("John");
    }
 
    @Test
    public void testFindAuthorsByLastName() {
        // Lenient stubbing
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        lenient().when(authorRepository.findByLastname("Doe")).thenReturn(authors);
 
        // Test method
        List<AuthorDTO> result = authorService.findAuthorsByLastName("Doe");
 
        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Doe", result.get(0).getLastname());
        verify(authorRepository, times(1)).findByLastname("Doe");
    }
 
  
 
    @Test
    public void testUpdateAuthorFirstName_NotFound() {
        // Lenient stubbing for the not found case
        lenient().when(authorRepository.findById(1)).thenReturn(Optional.empty());
 
        // Test method and assert exception
        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthorFirstName(1, "Jane"));
 
        
    }
 
    
 
    @Test
    public void testUpdateAuthorLastName_NotFound() {
        // Lenient stubbing
        lenient().when(authorRepository.findById(1)).thenReturn(Optional.empty());
 
        // Test method and assert exception
        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthorLastName(1, "Smith"));
 
        
    }
}