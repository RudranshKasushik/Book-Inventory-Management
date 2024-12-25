package com.cpg.bim.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="reviewer")
public class Reviewer {
	@Id
	@Column(name="reviewerid")
	int reviewerId;
	@Column(name="name")
	String name;
	@Column(name="employedby")
	String employedby;
	//@OneToMany(mappedBy="reviewerId")
	//@JsonIgnore
	//private Set<BookReview> reviewer;
 
	
	public Reviewer() {}
 
	public Reviewer(int reviewerId, String name, String employedby) {
		this.reviewerId = reviewerId;
		this.name = name;
		this.employedby = employedby;
	}
 
	public int getReviewerId() {
		return reviewerId;
	}
 
	public void setReviewerId(int reviewerId) {
		this.reviewerId = reviewerId;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getEmployedby() {
		return employedby;
	}
 
	public void setEmployedby(String employedby) {
		this.employedby = employedby;
	}

 
}
 