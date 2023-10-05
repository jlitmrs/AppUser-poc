package com.tmrs.poc.nextgen.jpa.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Entity
@Table(name="User_profile")
public class UserProfile {

	@Id
	@Column(name="user_id", columnDefinition="INTEGER")
	private Long userId;
	
	@Column(name="first_name", length=16, nullable=false, columnDefinition="VARCHAR(16)")
	private String firstName;
	
	@Column(name="last_name", length=16, nullable=false, columnDefinition="VARCHAR(16)")
	private String lastName;
	
	@Temporal(TemporalType.DATE)
	@Column(name="birthdate", columnDefinition="DATETIME")
	private Date birthDate;
	
	@Column(name="email", length=34, nullable=true, unique=true, columnDefinition="VARCHAR(34)")
	private String emaill;
	
	@Column(name="ssn", length=64, columnDefinition="varchar(64)")
	private String ssn;
}
