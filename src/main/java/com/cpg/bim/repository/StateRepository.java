package com.cpg.bim.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cpg.bim.entity.State;
import java.util.List;

public interface StateRepository extends JpaRepository<State,String>{
    Optional<State> findByStateCode(String stateCode);
	@Query(value = " select * from state where statecode= :stateCode",nativeQuery = true)
	Optional<List<State>>findByStateCode1(String stateCode);
}