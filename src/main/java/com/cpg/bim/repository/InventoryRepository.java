package com.cpg.bim.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.cpg.bim.entity.Inventory;
 
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
 
}

 