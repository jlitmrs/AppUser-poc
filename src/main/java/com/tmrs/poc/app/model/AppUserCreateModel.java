package com.tmrs.poc.app.model;

import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class AppUserCreateModel {
	
	@NotNull(message="Username can not be null")
	@Size(min=6, max=26, message="Username must be between 6 and 26 characters.")
	private String userName;
	
	//@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&-+=()])(?=\\\\S+$).{8,20}$")
	private String password;
	private Boolean active;
	private Boolean isAdmin;
	private Boolean isUserEditor;
	private Boolean isUserViewer;
	private Boolean isAppUser;
	
	@EqualsAndHashCode.Exclude
	private UserProfileModel profile;
}
