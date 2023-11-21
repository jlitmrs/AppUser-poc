package com.tmrs.poc.app.exception;

public class UserNotCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4233213708787207580L;
	private String userName;
	
	public UserNotCreatedException(String userName) {
		super();
		this.userName = userName;
	}
	
	
	public UserNotCreatedException(String userName, String message, Throwable e) {
		super(message, e);
		this.userName = userName;
	}
	
	
	public String getUserName() {
		return userName;
	}
}
