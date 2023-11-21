package com.tmrs.poc.app.jpa.entity;


import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name="app_usr")
public class AppUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", columnDefinition="INTEGER")
	private Long userId;
	
	@Column(name="user_name", length=16, unique=true, columnDefinition="varchar(16)")
	private String userName;
	
	@JsonIgnore
	@Column(name="pw_hash", nullable=false, length=64, columnDefinition="varchar(64)")
	private String passwordHash;
	
	@Column(name="active", columnDefinition="BOOLEAN DEFAULT TRUE")
	private Boolean active;
	
	@EqualsAndHashCode.Exclude
	@OneToOne(targetEntity=UserProfile.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private UserProfile profile;
	
	@EqualsAndHashCode.Exclude
	@OneToMany(targetEntity=UserPreference.class, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", referencedColumnName="user_id", insertable=true, updatable=true)
	private List<UserPreference> userPreferences;
	
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=SecurityRole.class)
	@JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<SecurityRole> roles;
}
