package com.tmrs.poc.nextgen.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmrs.poc.nextgen.jpa.entity.SecurityRole;

public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Long> {
	
	@Query("SELECT sec_role FROM SecurityRole sec_role WHERE sec_role.roleName = :roleName")
	public SecurityRole getByRoleName(@Param("roleName") String roleName);

}
