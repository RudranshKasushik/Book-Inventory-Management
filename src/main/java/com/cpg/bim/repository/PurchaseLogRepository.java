package com.cpg.bim.repository;

import java.util.List;
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.cpg.bim.entity.PurchaseLog;
import com.cpg.bim.entity.PurchaseLogID;
 
public interface PurchaseLogRepository  extends JpaRepository<PurchaseLog, PurchaseLogID> 
{
	@Query(value = "SELECT * FROM purchaselog WHERE userid = :userId", nativeQuery = true)
    List<PurchaseLog> findByUserId(@Param("userId") int userId);
	 // Method to find PurchaseLog by userId and inventoryId combination
    Optional<PurchaseLog> findByUsers_UseridAndInventory_Inventoryid(int userId, int inventoryId);
    @Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE purchaselog SET inventoryId = :newInventory WHERE userId = :userId")
	int updateInventoryByUser(@Param("userId") int userId, @Param("newInventory") int newinventory);
 
    @Query(value = "SELECT * FROM purchaselog WHERE inventoryId = :inventoryId", nativeQuery = true)
    Optional<PurchaseLog> findByInventory_InventoryId(int inventoryId);

 
 
}