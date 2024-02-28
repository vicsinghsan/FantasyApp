package com.fantasy.Fantasy.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fantasy.Fantasy.app.repository.ConfirmOtpRepository;
import com.fantasy.Fantasy.app.repository.UserRepository;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Async
	public void sendEmail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}


	@Autowired
	UserRepository userRepos;
	@Autowired
	ConfirmOtpRepository confirmOtpRepository;

	public String confirmEmail(String otp) {

		if (otp != null) {
			Long userid = confirmOtpRepository.findUserIdByOtp(otp);
			String dbOtp = confirmOtpRepository.findOtpByUserId(userid);
			if (userid != null && otp != null) {
				String email = userRepos.findEmailById(userid);
				if (userid != null && email != null) {
					if (otp.equals(dbOtp)) {
						userRepos.updateAccount_verifiedByEmail(email, true);
						confirmOtpRepository.delete(otp);

						return "Valid";
					}
				}
				{
					return "Invalid";
				}
			}
		}

		return null;

	}

}
