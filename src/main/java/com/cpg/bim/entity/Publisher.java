package com.cpg.bim.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "publisher")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property = "publisherid")
public class Publisher {
    
    @Id
    @Column(name = "publisherid")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "city")
    private String city;
    @Column(name = "statecode")
    private String state;

    @OneToMany(mappedBy = "publisher")
//    @JsonBackReference
    private Set<Book> books;

    public Publisher() {}
    
    public Publisher(int publisherid, String name, String city, String statecode, Set<Book> books) {
		super();
		this.id = publisherid;
		this.name = name;
		this.city = city;
		this.state = statecode;
		this.books = books;
	}

	// Getters and Setters
    public int getPublisherid() {
        return id;
    }

    public void setPublisherid(int publisherid) {
        this.id = publisherid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatecode() {
        return state;
    }

    public void setStatecode(String statecode) {
        this.state = statecode;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}