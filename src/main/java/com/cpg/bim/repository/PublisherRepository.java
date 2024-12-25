package com.cpg.bim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpg.bim.entity.Book;
import com.cpg.bim.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

	Optional<Publisher> findByName(String name);
	List<Publisher> findByCity(String city);
	List<Publisher> findByState(String state);
}