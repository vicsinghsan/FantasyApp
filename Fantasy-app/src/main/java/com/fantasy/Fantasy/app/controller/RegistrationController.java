package com.fantasy.Fantasy.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fantasy.Fantasy.app.model.User;
import com.fantasy.Fantasy.app.service.EmailService;
import com.fantasy.Fantasy.app.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "api")
public class RegistrationController {
	
	@Autowired
	UserService userService;
	@Autowired
	EmailService emailService;
	
	@PostMapping("/registration")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		String responseToClient = userService.validateUser(user);
		if (!(responseToClient.equals("valid"))) {
			return new ResponseEntity<String>(responseToClient, HttpStatus.OK);
		}
	
		else {
			responseToClient = userService.saveUser(user);
			return new ResponseEntity<String>(responseToClient, HttpStatus.OK);
		}
	}
	
	@PostMapping("/confirmOtp")
	public ResponseEntity<String> confirmEmail(@RequestBody String otps) {
		String confirmEmail = emailService.confirmEmail(otps);
		if(confirmEmail.equals("Valid")) {
			return new ResponseEntity<String>("Verified", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		}

	}
	

}
