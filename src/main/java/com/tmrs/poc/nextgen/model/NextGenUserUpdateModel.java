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
public class NextGenUserUpdateModel {
	private Long userId;
	private Boolean active;
	private Boolean isAdmin;
	
	@EqualsAndHashCode.Exclude
	private UserProfileModel profile;
}
