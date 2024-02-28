package com.fantasy.Fantasy.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fantasy.Fantasy.app.model.Login;
import com.fantasy.Fantasy.app.model.LoginDto;
import com.fantasy.Fantasy.app.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	
	
	@PostMapping(value = "/login")
	public ResponseEntity<LoginDto> generateToken(@RequestBody Login login) throws Exception{
		LoginDto loginDTO = userService.generateToken(login);
		return new ResponseEntity<LoginDto>(loginDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/logout")
	public ResponseEntity<String> Logout() throws Exception{
		String responseToClient = userService.isValidLogout();
		
		return new ResponseEntity<String>(responseToClient, HttpStatus.OK);
	}
	
	@GetMapping(value = "/login/google")
	   public ResponseEntity<Principal> user(Principal principal) {
		
		  return ResponseEntity.ok(principal);	   }
}