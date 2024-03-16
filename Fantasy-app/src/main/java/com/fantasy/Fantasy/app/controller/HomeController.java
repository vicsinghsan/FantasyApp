package com.fantasy.Fantasy.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fantasy.Fantasy.app.model.User;
import com.fantasy.Fantasy.app.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/info")
	public ResponseEntity<User> getUser(@RequestBody User user){
		String username = "";
		if(user!=null) {
			username = user.getUserName();
		}
		
		User usr = userService.getCurrentUser(username);
		if(usr!=null) {
			return ResponseEntity.ok(usr);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
	}

}
