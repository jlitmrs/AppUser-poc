package com.tmrs.poc.nextgen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NextGenUserSimpleModel {
	private Long userId;
	private String userName;
	private String passwordHash;
	private Boolean active;
	private Boolean isAdmin;
	
	@EqualsAndHashCode.Exclude
	private UserProfileModel profile;
}
