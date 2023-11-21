package com.tmrs.poc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmrs.poc.app.converter.UserProfileConverter;
import com.tmrs.poc.app.jpa.entity.UserProfile;
import com.tmrs.poc.app.jpa.repository.UserProfileRepository;
import com.tmrs.poc.app.model.UserProfileModel;

@Service
@Transactional
public class UserProfileService {

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private UserProfileConverter converter;
	
	
	public Boolean profileExiastById(Long id) {
		return userProfileRepository.existsById(id);
	}
	
	public UserProfile getProfileById(Long id) {
		return userProfileRepository.getProfileById(id).get();
	}
	
	public UserProfile saveProfile(Long id, UserProfileModel profileModel) {
		profileModel.setUserId(id);
		return userProfileRepository.save(converter.toEntity(profileModel));
	}
	
	public UserProfile updateProfile(Long id, UserProfileModel profileModel) {
		UserProfile profile = userProfileRepository.getReferenceById(id);
		
		if(profileModel.getFirstName() != null && !profile.getFirstName().equals(profileModel.getFirstName())) {
			profile.setFirstName(profileModel.getFirstName());
		}
		
		if(profileModel.getLastName() != null && !profile.getLastName().equals(profileModel.getLastName())) {
			profile.setLastName(profileModel.getLastName());
		}
		
		if(profileModel.getEmaill() != null && profile.getEmaill().equals(profileModel.getEmaill())) {
			profile.setEmaill(profileModel.getEmaill());
		}
		
		if(profileModel.getBirthDate() != null && !profile.getBirthDate().equals(profileModel.getBirthDate())) {
			profile.setBirthDate(profileModel.getBirthDate());
		}
		
		if(profileModel.getSsn() != null && !profile.getSsn().equals(profileModel.getSsn())) {
			profile.setSsn(profileModel.getSsn());
		}
		
		return userProfileRepository.save(profile);
	}
}
