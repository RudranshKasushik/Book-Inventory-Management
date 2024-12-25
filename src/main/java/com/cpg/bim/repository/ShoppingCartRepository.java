package com.cpg.bim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpg.bim.entity.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>
{
	List<ShoppingCart> findByUserId(int userId);
	
}
 