package com.cpg.bim.dto;

import com.cpg.bim.entity.Author;

public class AuthorDTO {
    private int id;
    private String lastname;
    private String firstname;
    private String photo;

    public AuthorDTO() {}
    
    public AuthorDTO(Author author) {
		this.id = author.getId();
		this.lastname = author.getLastname();
		this.firstname = author.getFirstname();
		this.photo = author.getPhoto();
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

