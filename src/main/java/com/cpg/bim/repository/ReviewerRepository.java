package com.cpg.bim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpg.bim.entity.Reviewer;

public interface ReviewerRepository extends JpaRepository<Reviewer,Integer> 
{
	Reviewer findByName(String Name);
 
}