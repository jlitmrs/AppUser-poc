package com.tmrs.poc.app.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmrs.poc.app.jpa.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	
	@Query("SELECT profile FROM UserProfile profile WHERE profile.userId = :id")
	public Optional<UserProfile> getProfileById(@Param("id") Long id);
}
