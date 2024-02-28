/**
 * 
 */
package com.fantasy.Fantasy.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fantasy.Fantasy.app.jwt.JwtUtil;
import com.fantasy.Fantasy.app.model.ConfirmOtp;
import com.fantasy.Fantasy.app.model.JWTLogin;
import com.fantasy.Fantasy.app.model.Login;
import com.fantasy.Fantasy.app.model.LoginDto;
import com.fantasy.Fantasy.app.model.PasswordDTO;
import com.fantasy.Fantasy.app.model.Role;
import com.fantasy.Fantasy.app.model.User;
import com.fantasy.Fantasy.app.repository.ConfirmOtpRepository;
import com.fantasy.Fantasy.app.repository.UserRepository;

/**
 * @author Vivek Singh
 *
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	EmailService emailService;
	@Autowired
	ConfirmOtpRepository confirmOtpRepository;

	@Override
	public List<User> findAllUsers() {

		List<User> users = new ArrayList<>();
		users = userRepository.findAll();
		for (User u : users) {
			if (u != null) {
				if (u.getUserRole().equals(Role.USER)) {
					users.add(u);
				}
			}
		}
		return users;
	}

	@Override
	public List<User> findAllEmployees() {
		List<User> users = new ArrayList<>();
		users = userRepository.findAll();
		for (User u : users) {
			if (u != null) {
				if (u.getUserRole().equals(Role.EMPLOYEE)) {
					users.add(u);
				}
			}
		}
		return users;
	}

	@Override
	public User findOne(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public User delete(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validateUser(User user) {
		if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getLastName() == null
				|| user.getLastName().trim().isEmpty() || user.getFirstName() == null
				|| user.getFirstName().trim().isEmpty() || user.getUserName() == null
				|| user.getUserName().trim().isEmpty() || user.getMobileNum() == null
				|| user.getAddress().trim().isEmpty() || user.getAddress() == null
				|| user.getPassword().trim().isEmpty() || user.getPassword() == null
				|| user.getMobileNum().trim().isEmpty() || !user.getEmail().matches("^(.+)@(.+)$")) {
			return "invalid";
		}
		if (!isEmailUnique(user.getEmail())) {
			return "emailNotUnique";
		} else if (findByUsername(user.getUserName()) != null) {
			return "usernameNotUnique";
		}
		return "valid";
	}

	private boolean isEmailUnique(String email) {
		List<User> allUsers = userRepository.findAll();
		for (User u : allUsers) {
			if (u.getEmail().equals(email))
				return false;
		}
		return true;
	}

	@Override
	public String validateUserUpdate(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validateEmployeeUpdate(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUser(User userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateEmployee(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginDto generateToken(Login login) {

		LoginDto loginDTO = new LoginDto();
		User user = new User();

		try {

			User userFromDB = findByUsername(login.getUsername());
			if (userFromDB.isAccountVerified()) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				if (!(userFromDB.getPassword().equals(login.getPassword()))) {
					if (encoder.matches(login.getPassword(), userFromDB.getPassword()) == false) {
						throw new Exception();
					} else {
						JWTLogin jwtDetails = new JWTLogin();
						jwtDetails.setRole(userFromDB.getUserRole().toString());
						jwtDetails.setUsername(userFromDB.getUserName());
						String token = jwtUtil.generateToken(jwtDetails);
						loginDTO = new LoginDto(token, "success");
					}
				} else {
					JWTLogin jwtDetails = new JWTLogin();
					jwtDetails.setRole(userFromDB.getUserRole().toString());
					jwtDetails.setUsername(userFromDB.getUserName());
					String token = jwtUtil.generateToken(jwtDetails);
					loginDTO = new LoginDto(token, "success");
				}
			}
		} catch (Exception e) {
			loginDTO = new LoginDto();
			loginDTO.setMessage("fail");
			return loginDTO;
		}

		return loginDTO;

	}

	@Override
	public String isValidLogout() {
		String responseToClient;
		if (getCurrentUser() != null) {
			SecurityContextHolder.clearContext();
			responseToClient = "valid";
		} else {
			responseToClient = "invalid";
		}
		return responseToClient;

	}

	@Override
	public String deactivateUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveUser(User user) {
		try {
			user.setUserRole(Role.USER);
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			userRepository.save(user);
			// creating the OTP
			String email = user.getEmail();
			ConfirmOtp confirmOtp = new ConfirmOtp(userRepository.findIdByEmail(email));
			String otp = confirmOtp.getOtp();
			// Saving OTP in DB
			confirmOtpRepository.save(confirmOtp);
			// Sending the mail
			sendMail(user, otp);
			return "success";
		} catch (Exception e) {
			return "fail";
		}
	}

	@Override
	public String saveEmployee(User user) {
		try {
			user.setUserRole(Role.EMPLOYEE);
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			userRepository.save(user);
			// send mail logic
			return "success";
		} catch (Exception e) {
			return "fail";
		}
	}

	@Override
	public String saveAdmin(User user) {
		try {
			user.setUserRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			userRepository.save(user);
			return "success";
		} catch (Exception e) {
			return "fail";
		}
	}

	@Override
	public void sendMail(User data, String otp) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(data.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setFrom("vics2811@gmail.com");
		mailMessage.setText("To confirm your account,please enter the Otp" + otp);

		emailService.sendEmail(mailMessage);
	}

	@Override
	public void setCurrentUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public String changePassword(PasswordDTO passwordDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
