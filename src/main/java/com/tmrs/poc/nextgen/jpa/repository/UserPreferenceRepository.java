package com.tmrs.poc.nextgen.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmrs.poc.nextgen.jpa.entity.UserPreference;
import com.tmrs.poc.nextgen.jpa.entity.id.PreferenceId;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, PreferenceId> {
	
	@Query("SELECT pref FROM UserPreference pref WHERE pref.userId = :userId")
	public List <UserPreference> getByUserId(@Param("userId") Long userId);

}
