package com.cpg.bim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpg.bim.entity.Book;

public interface BookRepository extends JpaRepository<Book, String>{

	Optional<Book> findByTitle(String title);
	List<Book> findByCategory_CatId(Integer catId);
	List<Book> findByPublisherId(Integer publisherId);
}
