package com.fantasy.Fantasy.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping(value = "/home")
	public ResponseEntity<List<User>> getUser(){
		
		List<User> users = userService.findAllEmployees();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		
	}

}
