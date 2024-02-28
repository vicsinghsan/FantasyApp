package com.fantasy.Fantasy.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fantasy.Fantasy.app.model.Login;
import com.fantasy.Fantasy.app.model.LoginDto;
import com.fantasy.Fantasy.app.model.PasswordDTO;
import com.fantasy.Fantasy.app.model.User;

@Service
public interface UserService {
	
	List<User> findAllUsers();
	List<User> findAllEmployees();
	
	User findOne(Long id);
	User findByUsername(String username);
	User delete(User user);
	User getCurrentUser();
	String validateUser(User user);
	String validateUserUpdate(User user);
	String validateEmployeeUpdate(User user);
	String updateUser(User userDTO);
	String updateEmployee(User user);
	LoginDto generateToken(Login login); 
	String isValidLogout();
	String deactivateUser(Long id);
	String saveUser(User user);
	String saveEmployee(User user);
	String saveAdmin(User user);
	public void setCurrentUser(User user);
	String changePassword(PasswordDTO passwordDTO);
	public void sendMail(User data, String otp);

}
