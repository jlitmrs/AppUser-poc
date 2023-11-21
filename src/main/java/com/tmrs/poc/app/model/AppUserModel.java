package com.tmrs.poc.app.model;

import java.util.List;

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
public class AppUserModel {

	private Long userId;
	private String userName;
	private String passwordHash;
	private Boolean active;
	private Boolean isAdmin;
	
	@EqualsAndHashCode.Exclude
	private List<UserPreferenceModel> userPreferences;
	
	@EqualsAndHashCode.Exclude
	private UserProfileModel profile;
}
