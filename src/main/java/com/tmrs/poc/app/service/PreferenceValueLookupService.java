package com.tmrs.poc.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmrs.poc.app.jpa.entity.PreferenceValueLookup;
import com.tmrs.poc.app.jpa.entity.id.PreferenceValueId;
import com.tmrs.poc.app.jpa.repository.PreferenceValueRepository;
import com.tmrs.poc.app.model.PreferenceValueModel;

@Service
@Transactional
public class PreferenceValueLookupService {
	
	@Autowired
	private PreferenceValueRepository preferenceValueRepository;
	
	
	public List<PreferenceValueLookup> getAll() {
		List<PreferenceValueLookup> lookup = preferenceValueRepository.findAll();
		return lookup;
	}
	
	/**
	 * This used to 
	 * @param key - the preferenceKey
	 * @return
	 */
	public List<PreferenceValueModel> getValueListByKey(String key) {
		List<PreferenceValueLookup> lookup = preferenceValueRepository.findByKey(key);
		return lookup.stream().map(valueLookup -> new PreferenceValueModel(valueLookup.getValue(), valueLookup.getDataType())).toList();
	}
	
	public List<PreferenceValueLookup> findByKey(String key) {
		return preferenceValueRepository.findByKey(key);
	}
	
	public Optional<PreferenceValueLookup> findByKeyAndValue(String key, String value) {
		return preferenceValueRepository.findByKeyAndValue(key, value);
	}
	
	public PreferenceValueLookup save(PreferenceValueLookup lookup) {
		return preferenceValueRepository.saveAndFlush(lookup);
	}
	
	public Boolean preferenceKeyValueExists(String key, String value) {
		return preferenceValueRepository.existsById(new PreferenceValueId(key, value));
	}
	
	public Boolean preferenceKeyValueExists(String key) {
		return (preferenceValueRepository.preferenceValueKeyExists(key) > 0);
	}

}
