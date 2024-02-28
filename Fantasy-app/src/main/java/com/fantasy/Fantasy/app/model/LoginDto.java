package com.fantasy.Fantasy.app.model;

public class LoginDto {
	

	
	private String token;
	
	private String message;
	
	public LoginDto() {
		
	}

	public LoginDto(String token, String message) {
		super();
		this.token = token;
		this.message = message;
		
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}



