package com.cpg.bim.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name="shoppingcart")
@Entity
public class ShoppingCart 
{
	@Id
	@Column(name="userid")
	private int userId;
	@Column(name="isbn")
	private String isbn;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="isbn",insertable = false, updatable = false)
	private Book book;
	public ShoppingCart() {}
	public ShoppingCart(int userId, String isbn) 
	{
		this.userId = userId;
		this.isbn = isbn;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}