package com.cpg.bim.entity;

import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="book")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property = "isbn")
public class Book {

	@Id
	@Column(name="isbn")
	private String isbn;

	@Column(name="title")
	private String title;

	@Column(name="description")
	private String description;

	@Column(name="edition")
	private String edition;

//	@ManyToOne(cascade = CascadeType.ALL)
	@ManyToOne
    @JoinColumn(name = "category")  
//	@JsonBackReference
    private Category category;

//	@ManyToOne(cascade = CascadeType.ALL)
	@ManyToOne
//	@JsonBackReference
	@JoinColumn(name="publisherid")
	private Publisher publisher;

	@OneToMany(mappedBy="book")
	private Set<Inventory> inventory;
	
	@OneToMany(mappedBy="book")
	private Set<ShoppingCart> cart;
	@OneToMany(mappedBy="isbn")
	private Set<BookAuthor> bookauthor;
	
	public Book() {
	  super();	
	}

	public Book(String isbn, String title, String description, Category category, String edition, Publisher publisherID) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.description = description;
		this.category = category;
		this.edition = edition;
		this.publisher = publisherID;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}
		
	public String getISBN() {
		return isbn;
	}

	public void setISBN(String iSBN) {
		isbn = iSBN;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Set<ShoppingCart> getCart() {
		return cart;
	}

	public void setCart(Set<ShoppingCart> cart) {
		this.cart = cart;
	}
//
//	public Set<BookReview> getBookreview() {
//		return bookreview;
//	}
//
//	public void setBookreview(Set<BookReview> bookreview) {
//		this.bookreview = bookreview;
//	}
//
	public Set<Inventory> getInventory() {
		return inventory;
	}

	public void setInventory(Set<Inventory> inventory) {
		this.inventory = inventory;
	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(cat, description, edition, ISBN, publisher, title);
//	}
//
//	public Set<BookAuthor> getBookauthor() {
//		return bookauthor;
//	}
//
//	public void setBookauthor(Set<BookAuthor> bookauthor) {
//		this.bookauthor = bookauthor;
//	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Book other = (Book) obj;
//		return Objects.equals(cat, other.cat) && Objects.equals(description, other.description)
//				&& Objects.equals(edition, other.edition) && Objects.equals(ISBN, other.ISBN)
//				&& Objects.equals(publisher, other.publisher) && Objects.equals(title, other.title);
//	}
	
}
