package com.tmrs.poc.nextgen.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="security_permissions")
public class SecurityPermission {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="permission_id", columnDefinition="INTEGER")
	private Long permissionId;
	
	@Column(name="permission_name", nullable=false, length=36, unique=true, columnDefinition="VARCHAR(36)")
	private String permissionName;
	
	@Column(name="permission_description", length=80, columnDefinition="VARCHAR(80)")
	private String permissionDescription;
	
}
