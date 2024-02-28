package com.fantasy.Fantasy.app.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fantasy.Fantasy.app.model.ConfirmOtp;

@Repository
public interface ConfirmOtpRepository extends CrudRepository<ConfirmOtp, Long>  {
	
	ConfirmOtp findByOtp(String otp);
	ConfirmOtp removeByOtp(String otp);
	
	@Query(value="Select userId from ConfirmOtp where otp = :otp")
	Long findUserIdByOtp(String otp);
	@Query(value="Select otp from ConfirmOtp where userId = :userId")
	String findOtpByUserId(long userId);
	
	@Modifying
	@Transactional
	@Query(value="Delete from ConfirmOtp a where a.otp = :otp")
	void delete(String otp);

}
