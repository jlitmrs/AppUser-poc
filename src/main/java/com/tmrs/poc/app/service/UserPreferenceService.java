package com.tmrs.poc.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmrs.poc.app.converter.UserPreferenceConverter;
import com.tmrs.poc.app.jpa.entity.UserPreference;
import com.tmrs.poc.app.jpa.entity.id.PreferenceId;
import com.tmrs.poc.app.jpa.repository.UserPreferenceRepository;
import com.tmrs.poc.app.model.UserPreferenceModel;

@Service
public class UserPreferenceService {
	
	@Autowired
	private UserPreferenceRepository userPreferenceRepository;
	
	@Autowired
	private UserPreferenceConverter userPreferenceConverter;
	
	
	public Optional<UserPreferenceModel> getPreference(Long userId, String preferenceKey) {
		Optional<UserPreference> entity = userPreferenceRepository.findById(new PreferenceId(userId, preferenceKey));
		
		if(entity.isPresent()) {
			return Optional.of(userPreferenceConverter.toModel(entity.get()));
		} else {
			return Optional.empty();
		}
	}
	
	public List <UserPreference> getAllUserPreferences(Long userId) {
		return userPreferenceRepository.getByUserId(userId);
	}
	
	public Optional<UserPreference> getUserPreference(Long userId, String prefKey) {
		return userPreferenceRepository.findById(new PreferenceId(userId, prefKey));
	}
	
	
	public UserPreference addUserPreference(UserPreferenceModel model) {
		if(model != null) {
			return userPreferenceRepository.saveAndFlush(userPreferenceConverter.toEntity(model));
		} else {
			throw new IllegalArgumentException("UserPreferenceModel is null");
		}
	}
	
	public UserPreference saveUserPreference(UserPreference entity) {
		return userPreferenceRepository.save(entity);
	}
	
	public void saveAllUserPreferences(Long userId, List <UserPreferenceModel> modelList) {
		if(modelList != null && !modelList.isEmpty()) {
			Set <UserPreference> prefs = new HashSet <UserPreference> (modelList.size());
			
			for(UserPreferenceModel model : modelList) {
				model.setUserId(userId);
				prefs.add(userPreferenceConverter.toEntity(model));
			}
			
			userPreferenceRepository.saveAllAndFlush(prefs);
		} else {
			throw new IllegalArgumentException("List<UserPreferenceModel> is null or empty.");
		}
	}
	
	public void deletePreference(Long userId, String preferenceKey) {
		userPreferenceRepository.deleteById(new PreferenceId(userId, preferenceKey));
	}

	public void deletePreferenceByUserId(Long userId) {
		userPreferenceRepository.deletePreferenceByUserId(userId);
	}
}
