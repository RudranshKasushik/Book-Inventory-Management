package com.cpg.bim.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookauthor")
public class BookAuthor {
 
    @EmbeddedId
    private BookAuthorId id;
 
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "isbn", insertable = false, updatable = false)  // Correct column name
    private Book isbn;
 
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authorid", insertable = false, updatable = false)  // Correct column name
    private Author authorid;
 
    @Column(name = "primaryauthor", columnDefinition = "CHAR(1)")
    private String primaryAuthor;
 
    // Getters and setters
    public BookAuthorId getId() {
        return id;
    }
 
    public void setId(BookAuthorId id) {
        this.id = id;
    }
 
    public Book getIsbn() {
        return isbn;
    }
 
    public void setIsbn(Book isbn) {
        this.isbn = isbn;
    }
 
    public Author getAuthorId() {
        return authorid;
    }
 
    public void setAuthorId(Author author_id) {
        this.authorid = author_id;
    }
 
    public String getPrimaryAuthor() {
        return primaryAuthor;
    }
 
    public void setPrimaryAuthor(String primaryAuthor) {
        this.primaryAuthor = primaryAuthor;
    }
 
    public static class BookAuthorId implements Serializable {
        private String isbn;
        private Integer authorid;
 
        @Override
        public int hashCode() {
            return Objects.hash(authorid, isbn);
        }
 
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            BookAuthorId other = (BookAuthorId) obj;
            return Objects.equals(authorid, other.authorid) && Objects.equals(isbn, other.isbn);
        }
 
        public String getIsbn() {
            return isbn;
        }
 
        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }
 
        public Integer getAuthorId() {
            return authorid;
        }
 
        public void setAuthorId(Integer authorId) {
            this.authorid = authorId;
        }
    }
}