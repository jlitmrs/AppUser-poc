package com.tmrs.poc.app.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmrs.poc.app.converter.UserProfileConverter;
import com.tmrs.poc.app.jpa.entity.ApplicationHistory;
import com.tmrs.poc.app.jpa.entity.ChangeType;
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
	
	@Autowired
	private ApplicationHistoryService historyService;
	
	
	public Boolean profileExiastById(Long id) {
		return userProfileRepository.existsById(id);
	}
	
	public UserProfile getProfileById(Long id) {
		return userProfileRepository.getProfileById(id).get();
	}
	
	public UserProfile saveProfile(Long id, UserProfileModel profileModel) {
		profileModel.setUserId(id);
		UserProfile profileEntity = userProfileRepository.save(converter.toEntity(profileModel));
		
		historyService.createHistoryRecord(
			new ApplicationHistory("user_profile", null, profileEntity.getUserId(), ChangeType.CREATE, 
				null, null, "AdminUser"));
		
		return profileEntity;
	}
	
	public UserProfile updateProfile(Long id, UserProfileModel profileModel) {
		UserProfile profile = userProfileRepository.getReferenceById(id);
		
		if(profileModel.getFirstName() != null && !profile.getFirstName().equals(profileModel.getFirstName())) {
			historyService.createHistoryRecord(
				new ApplicationHistory("user_profile", "first_name", profileModel.getUserId(), ChangeType.UPDATE, 
						profileModel.getFirstName(), profile.getFirstName(), "AdminUser"));
			profile.setFirstName(profileModel.getFirstName());
		}
		
		if(profileModel.getLastName() != null && !profile.getLastName().equals(profileModel.getLastName())) {
			historyService.createHistoryRecord(
					new ApplicationHistory("user_profile", "last_name", profileModel.getUserId(), ChangeType.UPDATE, 
							profileModel.getLastName(), profile.getLastName(), "AdminUser"));
			profile.setLastName(profileModel.getLastName());
		}
		
		if(profileModel.getEmaill() != null && profile.getEmaill().equals(profileModel.getEmaill())) {
			historyService.createHistoryRecord(
					new ApplicationHistory("user_profile", "email", profileModel.getUserId(), ChangeType.UPDATE, 
							profileModel.getEmaill(), profile.getEmaill(), "AdminUser"));
			profile.setEmaill(profileModel.getEmaill());
		}
		
		if(profileModel.getBirthDate() != null && !profile.getBirthDate().equals(profileModel.getBirthDate())) {
			String pattern = "MM/dd/yyyy HH:mm:ss";
			DateFormat df = new SimpleDateFormat(pattern);
			
			String newDate = df.format(profileModel.getBirthDate());
			String oldDate = df.format(profile.getBirthDate());
			
			historyService.createHistoryRecord(
				new ApplicationHistory("user_profile", "birthdate", profileModel.getUserId(), ChangeType.UPDATE, 
						newDate, oldDate, "AdminUser"));
			
			profile.setBirthDate(profileModel.getBirthDate());
		}
		
		if(profileModel.getSsn() != null && !profile.getSsn().equals(profileModel.getSsn())) {
			historyService.createHistoryRecord(
				new ApplicationHistory("user_profile", "ssn", profileModel.getUserId(), ChangeType.UPDATE, 
					profileModel.getSsn(), profile.getSsn(), "AdminUser"));
			
			profile.setSsn(profileModel.getSsn());
		}
		
		return userProfileRepository.save(profile);
	}
}
