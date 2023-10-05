package com.tmrs.poc.nextgen.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDoesNotExistException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4713389277962991481L;
	private String userName;
	private Long id;

	public UserDoesNotExistException(String message, String username, Long id) {
		super(message);
		this.setId(id);
		this.setUserName(username);
	}
	

	
	@Override
	public String getMessage() {
		return "User with "+getParameter()+" does not exists";
	}
	
	private String getParameter() {
		if(id != null) {
			return "id["+id.toString()+"]";
		} else {
			return "userName["+userName+"]";
		}
	}

}
