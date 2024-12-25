package com.cpg.bim.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="author")
public class Author {
 
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "authorid")
    private int id;
 
    @Column(name = "lastname")
    private String lastname;
 
    @Column(name = "firstname")
    private String firstname;
 
    @Column(name="photo")
    private String photo;
 
//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
//    @JsonIgnore  // Prevent infinite recursion
//    private List<Book> books;
    @OneToMany(mappedBy = "authorid")
    private Set<BookAuthor> bauthors;
    public Author() {}
 
//    public Author(int i, String string, String string2, String string3) {}
 
    public Author(Set<BookAuthor> bauthors,  String lastname, String firstname, String photo, int authorId) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.photo = photo;
        this.id= authorId;
        this.bauthors= bauthors;
    }
 
    public Author(int authorId, String lastname, String firstname, String photo) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public Set<BookAuthor> getBauthors() {
		return bauthors;
	}
 
	public void setBauthors(Set<BookAuthor> bauthors) {
		this.bauthors = bauthors;
	}
 
 
    public String getLastname() {
        return lastname;
    }
 
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
 
    public String getFirstname() {
        return firstname;
    }
 
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
 
    public String getPhoto() {
        return photo;
    }
 
    public void setPhoto(String photo) {
        this.photo = photo;
    }
 
    public Set<BookAuthor> getBooksAuthors() {
        return bauthors;
    }
 
    public void setBookAuthors(Set<BookAuthor> books) {
        this.bauthors = books;
    }
}