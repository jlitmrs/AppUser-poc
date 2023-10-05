package com.tmrs.poc.nextgen.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7443379049518397254L;
	private String userName;
	
	public UserAlreadyExistException(String message, String username) {
		super(message);
		this.setUserName(username);
	}

	
	@Override
	public String getMessage() {
		return "User with userName["+userName+"] already exists";
	}
	
	
}
