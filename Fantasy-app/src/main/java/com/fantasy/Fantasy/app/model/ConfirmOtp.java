package com.fantasy.Fantasy.app.model;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="Otp")
public class ConfirmOtp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OtpId")
	private Long OtpId;
	@NotNull
	private Long userId;
	@NotNull
	private String otp;
	@NotNull
	
	private Date createdDate = new Date();
	public ConfirmOtp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ConfirmOtp(Long otpId, Long userId, String otp, Date createdDate) {
		super();
		OtpId = otpId;
		this.userId = userId;
		this.otp = otp;
		this.createdDate = createdDate;
	}
	public ConfirmOtp(Long id) {
		this.userId = id;
		createdDate = new Date();
		otp = String.format("%04d", new Random().nextInt(9999));
	}
	public Long getOtpId() {
		return OtpId;
	}
	public void setOtpId(Long otpId) {
		OtpId = otpId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "ConfirmOtp [OtpId=" + OtpId + ", userId=" + userId + ", otp=" + otp + ", createdDate=" + createdDate
				+ "]";
	}
	
	
	
}
	