package com.cpg.bim.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpg.bim.entity.PermRole;

public interface PermroleRepository extends JpaRepository<PermRole, Integer> {

    Optional<PermRole> findByRolenumber(Integer rolenumber);
}
 