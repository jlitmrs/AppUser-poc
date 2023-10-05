package com.tmrs.poc.nextgen.model;

import java.util.Map;
import java.util.Set;

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
public class LoginUserModel {
	
	private long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private Set <String> permissions;
	private Set <String> roles;
	private Map <String, String> userPreferences;

}
