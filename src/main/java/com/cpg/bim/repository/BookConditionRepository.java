package com.cpg.bim.repository;
 
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.cpg.bim.entity.Bookcondition;
 
public interface BookConditionRepository extends JpaRepository<Bookcondition,Integer> {
	Optional<Bookcondition> findByRanks(int ranks);
 
}