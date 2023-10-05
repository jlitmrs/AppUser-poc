package com.tmrs.poc.nextgen.jpa.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="SecurityRole")
@Table(name="security_role")
public class SecurityRole {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id", columnDefinition="INTEGER")
	private Long roleId;
	
	@Column(name="role_name", length=32, unique=true, nullable=false, insertable=true, updatable=false, columnDefinition="VARCHAR(32)")
	private String roleName;
	
	@Column(name="role_description", length=128, columnDefinition="VARCHAR(128)")
	private String roleDescription;
		
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=SecurityPermission.class)
	@JoinTable(name="role_permission", joinColumns=@JoinColumn(name="role_id"), inverseJoinColumns=@JoinColumn(name="permission_id"))
	private Set <SecurityPermission> permissions;
}
