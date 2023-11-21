package com.tmrs.poc.app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FieldValueInvalidException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3414779670669309769L;
	private String fieldName;
	private String oldValue;
	private String newValue;
	
	public FieldValueInvalidException(String message, String fieldName, String oldValue, String newValue) {
		super(message);
		this.setFieldName(fieldName);
		this.setOldValue(oldValue);
		this.setNewValue(newValue);
	}
	
	
	public FieldValueInvalidException(String fieldName, String oldValue, String newValue) {
		super("Invalid field value field[%s] current[%s] new[%s]");
		this.setFieldName(fieldName);
		this.setOldValue(oldValue);
		this.setNewValue(newValue);
	}


	@Override
	public String getMessage() {
		String message = super.getMessage();
		return String.format(message, fieldName, oldValue, newValue);
	}
}
