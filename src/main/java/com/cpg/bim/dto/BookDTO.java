package com.cpg.bim.dto;

public class BookDTO {

    private String isbn;
    private String title;
    private String description;
    private String edition;
    private CategoryDTO category;
    private PublisherDTO publisher;

    // Default constructor
    public BookDTO() {}

    // Parameterized constructor
    public BookDTO(String isbn, String title, String description, String edition, CategoryDTO category, PublisherDTO publisher) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.edition = edition;
        this.category = category;
        this.publisher = publisher;
    }

    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
    }
}
