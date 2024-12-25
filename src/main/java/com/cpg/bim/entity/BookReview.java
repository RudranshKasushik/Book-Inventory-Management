package com.cpg.bim.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="bookreview")
public class BookReview
{
	@Id
	private String isbn;
	private int reviewerid;
	private int rating;
	private String comments;
	public BookReview() {}
	public BookReview(String isbn, int reviewerid, int rating, String comments) {
		this.isbn = isbn;
		this.reviewerid = reviewerid;
		this.rating = rating;
		this.comments = comments;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getReviewerid() {
		return reviewerid;
	}
	public void setReviewerid(int reviewerid) {
		this.reviewerid = reviewerid;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Object getBook() {
		// TODO Auto-generated method stub
		return null;
	}	
}