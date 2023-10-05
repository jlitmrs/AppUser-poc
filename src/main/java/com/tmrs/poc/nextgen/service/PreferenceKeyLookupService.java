package com.tmrs.poc.nextgen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmrs.poc.nextgen.jpa.entity.PreferenceKeyLookup;
import com.tmrs.poc.nextgen.jpa.repository.PreferenceKeyRepository;

@Service
@Transactional
public class PreferenceKeyLookupService {
	
	@Autowired
	private PreferenceKeyRepository preferenceKeyRepository;
	
	
	public List<PreferenceKeyLookup> getAll() {
		return preferenceKeyRepository.findAll();
	}
	
	public PreferenceKeyLookup findByKey(String key) {
		return preferenceKeyRepository.findByKey(key);
	}
	
	public PreferenceKeyLookup save(PreferenceKeyLookup lookup) {
		return preferenceKeyRepository.saveAndFlush(lookup);
	}
	
	public Boolean preferenceKeyExists(String key) {
		return preferenceKeyRepository.existsById(key);
	}

}
