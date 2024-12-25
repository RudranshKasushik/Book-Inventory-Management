package com.cpg.bim.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cpg.bim.entity.Users;
public interface UserRepository extends JpaRepository<Users,Integer>
{
	 boolean existsByUsername(String username);
	 @Query(nativeQuery = true, value = "select * from users where username = ?1 and password = ?2")
	 Users findByUserNameAndPassword(String username, String password);

}