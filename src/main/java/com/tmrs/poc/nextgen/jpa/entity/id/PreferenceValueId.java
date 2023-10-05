package com.tmrs.poc.nextgen.jpa.entity.id;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class PreferenceValueId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String preferenceKey;
	private String value;
	
	public PreferenceValueId(String preferenceKey, String value) {
		this.preferenceKey = preferenceKey;
		this.value = value;
	}
}
