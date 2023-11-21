package com.tmrs.poc.app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginModel {
	@NotBlank(message="Username can not be empty")
	private String username;
	
	@NotNull(message="Password can not be empty")
	private String password;
}
