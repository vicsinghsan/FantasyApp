/**
 * 
 */
package com.fantasy.Fantasy.app.model;

/**
 * @author Vivek Singh
 *
 */
public class PasswordDTO {

	private String oldPassword;
	private String newPassword;
	
	public PasswordDTO() {
		
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
