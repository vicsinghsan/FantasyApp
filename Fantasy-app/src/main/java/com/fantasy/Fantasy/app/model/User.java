package com.fantasy.Fantasy.app.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name="fantasy_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	
	public User(User user) {
	
			this(user.getId(), user.getFirstName(), user.getLastName(),
					user.getEmail(), user.getUserName(), user.getPassword(), user.getMobileNum(), user.getAddress(), user.getUserRole(),user.accountVerified);
		
	}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String firstName;
	
	private String lastName;
	@NonNull
	private String userName;
	@NonNull
	private String email;
	@NonNull
	private String password;
	
	private String address;
	@NonNull
	private String mobileNum;
	
	private Role userRole;
	
	private boolean accountVerified = false;
	

}
