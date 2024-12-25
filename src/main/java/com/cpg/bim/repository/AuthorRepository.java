package com.cpg.bim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cpg.bim.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findByFirstname(String firstname);
    List<Author> findByLastname(String lastname);
 
    @Query(nativeQuery = true, value = "SELECT b.* FROM book b " +
            "JOIN bookauthor ba ON b.isbn = ba.isbn " +
            "JOIN author a ON ba.authorid = a.authorid " +
            "WHERE a.authorid = :authorid")
    List<Object[]> findBooksByAuthorid(@Param("authorid") Integer authorid);
    Optional<Author> findById(int id);

    Optional<Author> findByFirstnameAndLastname(String firstname, String lastname);

    
 
}