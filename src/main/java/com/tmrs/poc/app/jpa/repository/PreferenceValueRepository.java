package com.tmrs.poc.app.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmrs.poc.app.jpa.entity.PreferenceValueLookup;
import com.tmrs.poc.app.jpa.entity.id.PreferenceValueId;

public interface PreferenceValueRepository extends JpaRepository<PreferenceValueLookup, PreferenceValueId> {
	
	@Query("SELECT lookup FROM PreferenceValueLookup lookup WHERE lookup.preferenceKey = :key AND lookup.value = :value")
	public Optional<PreferenceValueLookup> findByKeyAndValue(@Param("key") String key, @Param("value") String value);
	
	@Query("SELECT lookup FROM PreferenceValueLookup lookup WHERE lookup.preferenceKey = :key")
	public List<PreferenceValueLookup> findByKey(@Param("key") String key);
	
	@Query("SELECT COUNT(lookup) FROM PreferenceValueLookup lookup")
	public Integer preferenceValueKeyExists(@Param("key") String key);
}
