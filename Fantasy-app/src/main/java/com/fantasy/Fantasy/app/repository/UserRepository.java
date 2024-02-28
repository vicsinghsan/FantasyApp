package com.fantasy.Fantasy.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fantasy.Fantasy.app.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String userName);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.accountVerified = :status WHERE u.email = :email")
	Integer updateAccount_verifiedByEmail(String email,boolean status);

	@Query(value = "Select email from User where id =:id")
	String findEmailById(Long id);
	
	@Query(value = "SELECT id from User where email=:email")
	Long findIdByEmail(String  email);
	

}
