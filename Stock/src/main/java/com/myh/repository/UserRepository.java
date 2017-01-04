package com.myh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myh.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User>{

	User findByName(String username);
	
	User findByNameAndRole(String username, String role);
}
