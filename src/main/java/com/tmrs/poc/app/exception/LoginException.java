package com.tmrs.poc.app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3843739002205103608L;
	private String username; 
	private String password;
	private String salt;
	
	public LoginException(String message, String username, String password, String salt) {
		super(message);
		setUsername(username);
		setPassword(password);
		setSalt(salt);
	}
	
	@Override
	public String getMessage() {
		return String.format(super.getMessage(), username, password, salt);
	}

}
