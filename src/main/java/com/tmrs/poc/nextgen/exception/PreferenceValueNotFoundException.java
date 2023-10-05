package com.tmrs.poc.nextgen.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class PreferenceValueNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 756774175540876353L;
	private String key;
	private String value;
	
	
	public PreferenceValueNotFoundException() {super();}
	
	public PreferenceValueNotFoundException(String key) {
		super("PreferenceValueLookup key["+key+"] does not exist.");
		this.key = key;
	}
	
	
	public PreferenceValueNotFoundException(String key, String value) {
		super("PreferenceValueLookup key["+key+"] and value["+value+"] does not exist.");
		this.key = key;
		this.value = value;
	}

}
