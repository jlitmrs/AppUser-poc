package com.tmrs.poc.app.model;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserProfileCreateModel {
	private String firstName;
	private String lastName;
	
	@PastOrPresent(message="Birthdate cannot be be in the future.")
	private Date birthDate;
	
	@NotBlank
	@Email(regexp="/^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$/", message="Invalid email address.")
	private String emaill;
	
	@NotBlank
	@Pattern(regexp="^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$", message="Malformed SSN.")
	private String ssn;
}
