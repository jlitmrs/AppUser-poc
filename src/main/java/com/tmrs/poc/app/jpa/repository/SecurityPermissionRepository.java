package com.tmrs.poc.app.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmrs.poc.app.jpa.entity.SecurityPermission;

public interface SecurityPermissionRepository extends JpaRepository<SecurityPermission, Long> {

}
