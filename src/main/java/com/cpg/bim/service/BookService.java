package com.cpg.bim.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Transactional
    public Map<String, String> addBook(BookDTO bookDTO) {
        Map<String, String> response = new HashMap<>();
        Book book = convertToEntity(bookDTO);
        Optional<Category> categoryOptional = categoryRepository.findById(book.getCategory().getId());
        Optional<Publisher> publisherOptional = publisherRepository.findById(book.getPublisher().getPublisherid());

        if (!categoryOptional.isPresent()) {
            response.put("code", "CATEGORYNOTFOUND");
            response.put("message", "Category with ID " + book.getCategory().getId() + " does not exist.");
            return response;
        }

        if (!publisherOptional.isPresent()) {
            response.put("code", "PUBLISHERNOTFOUND");
            response.put("message", "Publisher with ID " + book.getPublisher().getPublisherid() + " does not exist.");
            return response;
        }

        book.setCategory(categoryOptional.get());
        book.setPublisher(publisherOptional.get());

        Book savedBook = bookRepo.save(book);

        if (savedBook != null) {
            response.put("code", "POSTSUCCESS");
            response.put("message", "Book added successfully");
        } else {
            response.put("code", "POSTFAIL");
            response.put("message", "Failed to add book");
        }

        return response;
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepo.findAll();
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDTO getBookById(String isbn) throws BookNotFoundException {
        Book book = bookRepo.findById(isbn).orElseThrow(() -> new BookNotFoundException());
        return convertToDTO(book);
    }

    @Transactional
    public BookDTO getBookByTitle(String title) throws BookNotFoundException {
        Book book = bookRepo.findByTitle(title).orElseThrow(() -> new BookNotFoundException());
        return convertToDTO(book);
    }

    @Transactional
    public List<BookDTO> getBookByCategory(int categoryId) throws BookNotFoundException {
        List<Book> books = bookRepo.findByCategory_CatId(categoryId);
        if (books == null) {
            throw new BookNotFoundException();
        }
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<BookDTO> getBookByPublisher(int publisherId) throws BookNotFoundException {
        List<Book> books = bookRepo.findByPublisherId(publisherId);
        if (books == null) {
            throw new BookNotFoundException();
        }
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public BookDTO updateBookTitle(String isbn, String newTitle) throws BookNotFoundException {
        Book book = bookRepo.findById(isbn).orElseThrow(() -> new BookNotFoundException("Book not found"));
        book.setTitle(newTitle);
        return convertToDTO(bookRepo.save(book));
    }

    @Transactional
    public BookDTO updatedBookDescription(String isbn, String newDesc) throws BookNotFoundException {
        Book book = bookRepo.findById(isbn).orElseThrow(() -> new BookNotFoundException("Book not found"));
        book.setDescription(newDesc);
        return convertToDTO(bookRepo.save(book));
    }

    @Transactional
    public BookDTO updatedBookCategory(String isbn, int newCatId) throws BookNotFoundException {
        Book book = bookRepo.findById(isbn).orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));
        Category newCategory = categoryRepository.findById(newCatId).orElseThrow(() -> new BookNotFoundException("Category not found with ID: " + newCatId));
        book.setCategory(newCategory);
        return convertToDTO(bookRepo.save(book));
    }

    @Transactional
    public BookDTO updateBookPublisher(String isbn, PublisherDTO newPublisherDTO) throws BookNotFoundException {
        Book book = bookRepo.findById(isbn).orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));
        Publisher newPublisher = new Publisher();
        newPublisher.setPublisherid(newPublisherDTO.getId());
        newPublisher.setName(newPublisherDTO.getName());
        newPublisher.setCity(newPublisherDTO.getCity());
        newPublisher.setStatecode(newPublisherDTO.getState());
        publisherRepository.save(newPublisher);
        book.setPublisher(newPublisher);
        return convertToDTO(bookRepo.save(book));
    }

    @Transactional
    public BookDTO updatedBookEdition(String isbn, String newEdition) throws BookNotFoundException {
        Book book = bookRepo.findById(isbn).orElseThrow(() -> new BookNotFoundException("Book not found"));
        book.setEdition(newEdition);
        return convertToDTO(bookRepo.save(book));
    }

    private BookDTO convertToDTO(Book book) {
        CategoryDTO categoryDTO = new CategoryDTO(book.getCategory().getId(), book.getCategory().getDescription());
        PublisherDTO publisherDTO = new PublisherDTO(book.getPublisher().getPublisherid(), book.getPublisher().getName(), book.getPublisher().getCity(), book.getPublisher().getStatecode());
        return new BookDTO(book.getISBN(), book.getTitle(), book.getDescription(), book.getEdition(), categoryDTO, publisherDTO);
    }

    private Book convertToEntity(BookDTO bookDTO) {
        Category category = new Category();
        category.setId(bookDTO.getCategory().getId());
        category.setDescription(bookDTO.getCategory().getDescription());

        Publisher publisher = new Publisher();
        publisher.setPublisherid(bookDTO.getPublisher().getId());
        publisher.setName(bookDTO.getPublisher().getName());
        publisher.setCity(bookDTO.getPublisher().getCity());
        publisher.setStatecode(bookDTO.getPublisher().getState());

        return new Book(bookDTO.getIsbn(), bookDTO.getTitle(), bookDTO.getDescription(), category, bookDTO.getEdition(), publisher);
    }
}
