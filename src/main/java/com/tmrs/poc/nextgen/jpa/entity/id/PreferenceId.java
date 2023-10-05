package com.tmrs.poc.nextgen.jpa.entity.id;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode
@NoArgsConstructor
public class PreferenceId implements Serializable {
	
	private static final long serialVersionUID = 3043831089606182178L;
	private Long userId;
	private String preferenceKey;
	
	public PreferenceId(Long userId, String preferenceKey) {
		this();
		this.userId = userId;
		this.preferenceKey = preferenceKey;
	}
	
}
