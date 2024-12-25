package com.cpg.bim.service.test;

import com.cpg.bim.dto.BookDTO;
import com.cpg.bim.dto.CategoryDTO;
import com.cpg.bim.dto.PublisherDTO;
import com.cpg.bim.entity.Book;
import com.cpg.bim.entity.Category;
import com.cpg.bim.entity.Publisher;
import com.cpg.bim.exception.BookNotFoundException;
import com.cpg.bim.repository.BookRepository;
import com.cpg.bim.repository.CategoryRepository;
import com.cpg.bim.repository.PublisherRepository;
import com.cpg.bim.service.BookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepo;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private BookService bookService;

    private BookDTO bookDTO;
    private Book book;
    private Category category;
    private Publisher publisher;

    @BeforeEach
    public void setup() {
        category = new Category();
        category.setId(1);
        category.setDescription("Fiction");

        publisher = new Publisher();
        publisher.setPublisherid(1);
        publisher.setName("O'Reilly Media");
        publisher.setCity("Sebastopol");
        publisher.setStatecode("CA");

        bookDTO = new BookDTO("1234567890", "Test Title", "Test Description", "1st", 
                              new CategoryDTO(1, "Fiction"), new PublisherDTO(1, "O'Reilly Media", "Sebastopol", "CA"));

        book = new Book();
        book.setISBN("1234567890");
        book.setTitle("Test Title");
        book.setDescription("Test Description");
        book.setEdition("1st");
        book.setCategory(category);
        book.setPublisher(publisher);
    }

    @Test
    public void testAddBookSuccess() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        when(publisherRepository.findById(anyInt())).thenReturn(Optional.of(publisher));
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        Map<String, String> response = bookService.addBook(bookDTO);

        assertEquals("POSTSUCCESS", response.get("code"));
        assertEquals("Book added successfully", response.get("message"));
    }

    @Test
    public void testAddBookCategoryNotFound() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        Map<String, String> response = bookService.addBook(bookDTO);

        assertEquals("CATEGORYNOTFOUND", response.get("code"));
    }

    @Test
    public void testAddBookPublisherNotFound() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        when(publisherRepository.findById(anyInt())).thenReturn(Optional.empty());

        Map<String, String> response = bookService.addBook(bookDTO);

        assertEquals("PUBLISHERNOTFOUND", response.get("code"));
    }

    @Test
    public void testGetAllBooks() {
        when(bookRepo.findAll()).thenReturn(Collections.singletonList(book));

        List<BookDTO> bookDTOList = bookService.getAllBooks();

        assertFalse(bookDTOList.isEmpty());
        assertEquals(1, bookDTOList.size());
        assertEquals("1234567890", bookDTOList.get(0).getIsbn());
    }

    @Test
    public void testGetAllBooksNotFound() {
        when(bookRepo.findAll()).thenReturn(Collections.emptyList());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getAllBooks();
        });
    }

    @Test
    public void testGetBookById() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.of(book));

        BookDTO foundBookDTO = bookService.getBookById("1234567890");

        assertNotNull(foundBookDTO);
        assertEquals("1234567890", foundBookDTO.getIsbn());
    }

    @Test
    public void testGetBookByIdNotFound() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookById("1234567890");
        });
    }

    @Test
    public void testGetBookByTitle() {
        when(bookRepo.findByTitle(anyString())).thenReturn(Optional.of(book));

        BookDTO foundBookDTO = bookService.getBookByTitle("Test Title");

        assertNotNull(foundBookDTO);
        assertEquals("Test Title", foundBookDTO.getTitle());
    }

    @Test
    public void testGetBookByTitleNotFound() {
        when(bookRepo.findByTitle(anyString())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookByTitle("Test Title");
        });
    }

    @Test
    public void testGetBookByCategory() {
        when(bookRepo.findByCategory_CatId(anyInt())).thenReturn(Collections.singletonList(book));

        List<BookDTO> bookDTOList = bookService.getBookByCategory(1);

        assertFalse(bookDTOList.isEmpty());
        assertEquals(1, bookDTOList.size());
        assertEquals("1234567890", bookDTOList.get(0).getIsbn());
    }

    @Test
    public void testGetBookByCategoryNotFound() {
        when(bookRepo.findByCategory_CatId(anyInt())).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookByCategory(1);
        });
    }

    @Test
    public void testGetBookByPublisher() {
        when(bookRepo.findByPublisherId(anyInt())).thenReturn(Collections.singletonList(book));

        List<BookDTO> bookDTOList = bookService.getBookByPublisher(1);

        assertFalse(bookDTOList.isEmpty());
        assertEquals(1, bookDTOList.size());
        assertEquals("1234567890", bookDTOList.get(0).getIsbn());
    }

    @Test
    public void testGetBookByPublisherNotFound() {
        when(bookRepo.findByPublisherId(anyInt())).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookByPublisher(1);
        });
    }

    @Test
    public void testUpdateBookTitle() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.of(book));
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        BookDTO updatedBookDTO = bookService.updateBookTitle("1234567890", "New Title");

        assertNotNull(updatedBookDTO);
        assertEquals("New Title", updatedBookDTO.getTitle());
    }

    @Test
    public void testUpdateBookTitleNotFound() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBookTitle("1234567890", "New Title");
        });
    }

    @Test
    public void testUpdatedBookDescription() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.of(book));
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        BookDTO updatedBookDTO = bookService.updatedBookDescription("1234567890", "New Description");

        assertNotNull(updatedBookDTO);
        assertEquals("New Description", updatedBookDTO.getDescription());
    }

    @Test
    public void testUpdatedBookDescriptionNotFound() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.updatedBookDescription("1234567890", "New Description");
        });
    }

    @Test
    public void testUpdatedBookCategory() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.of(book));
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        BookDTO updatedBookDTO = bookService.updatedBookCategory("1234567890", 1);

        assertNotNull(updatedBookDTO);
        assertEquals(1, updatedBookDTO.getCategory().getId());
    }

    @Test
    public void testUpdatedBookCategoryBookNotFound() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.updatedBookCategory("1234567890", 1);
        });
    }

    @Test
    public void testUpdatedBookCategoryNotFound() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.of(book));
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.updatedBookCategory("1234567890", 1);
        });
    }

    @Test
    public void testUpdateBookPublisher() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.of(book));
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        PublisherDTO newPublisherDTO = new PublisherDTO(2, "New Publisher", "New City", "NY");
        BookDTO updatedBookDTO = bookService.updateBookPublisher("1234567890", newPublisherDTO);

        assertNotNull(updatedBookDTO);
        assertEquals("New Publisher", updatedBookDTO.getPublisher().getName());
    }

    @Test
    public void testUpdateBookPublisherNotFound() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBookPublisher("1234567890", new PublisherDTO(2, "New Publisher", "New City", "NY"));
        });
    }

    @Test
    public void testUpdatedBookEdition() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.of(book));
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        BookDTO updatedBookDTO = bookService.updatedBookEdition("1234567890", "2nd");

        assertNotNull(updatedBookDTO);
        assertEquals("2nd", updatedBookDTO.getEdition());
    }

    @Test
    public void testUpdatedBookEditionNotFound() {
        when(bookRepo.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.updatedBookEdition("1234567890", "2nd");
        });
    }
}
