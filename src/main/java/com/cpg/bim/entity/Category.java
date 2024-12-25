package com.cpg.bim.entity;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property = "catid")
public class Category {

    @Id
    @Column(name = "catid")
    private int catId;

    @Column(name = "catdescription")
    private String catdescription;

//    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "category")
//    @JsonBackReference
    private Set<Book> books;

    public Category() {
    }

    public Category(int id, String description) {
        this.catId = id;
        this.catdescription = description;
    }

    // Getters and Setters

    public int getId() {
        return catId;
    }

    public void setId(int id) {
        this.catId = id;
    }

    public String getDescription() {
        return catdescription;
    }

    public void setDescription(String description) {
        this.catdescription = description;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public int hashCode() {
        return Objects.hash(catId, catdescription);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Category other = (Category) obj;
        return catId == other.catId && Objects.equals(catdescription, other.catdescription);
    }
}
