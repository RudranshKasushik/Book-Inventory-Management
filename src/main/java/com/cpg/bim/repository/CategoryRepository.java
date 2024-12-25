package com.cpg.bim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpg.bim.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}