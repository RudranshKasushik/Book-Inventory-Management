package com.cpg.bim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpg.bim.entity.BookAuthor;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, String>{

}
