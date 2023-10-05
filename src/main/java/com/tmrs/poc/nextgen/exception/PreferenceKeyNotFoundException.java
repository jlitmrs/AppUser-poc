package com.tmrs.poc.nextgen.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PreferenceKeyNotFoundException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4912175033531380323L;
	private String key;
	
	public PreferenceKeyNotFoundException(String key) {
		super("PreferenceValue key["+key+"] does not exist.");
		this.key = key;
	}
	
	
}
