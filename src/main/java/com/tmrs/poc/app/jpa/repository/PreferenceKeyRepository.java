package com.tmrs.poc.app.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmrs.poc.app.jpa.entity.PreferenceKeyLookup;

public interface PreferenceKeyRepository extends JpaRepository <PreferenceKeyLookup, String> {

	@Query("SELECT lookup from PreferenceKeyLookup lookup WHERE lookup.preferenceKey = :key")
	public PreferenceKeyLookup findByKey(@Param("key") String key);
	
}
