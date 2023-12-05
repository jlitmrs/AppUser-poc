package com.tmrs.poc.app.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmrs.poc.app.converter.UserProfileConverter;
import com.tmrs.poc.app.exception.FieldValueInvalidException;
import com.tmrs.poc.app.jpa.entity.ApplicationHistory;
import com.tmrs.poc.app.jpa.entity.enumeration.ChangeType;
import com.tmrs.poc.app.jpa.entity.UserProfile;
import com.tmrs.poc.app.jpa.repository.UserProfileRepository;
import com.tmrs.poc.app.model.UserProfileModel;
import com.tmrs.poc.app.util.PasswordUtil;

@Service
@Transactional
public class UserProfileService {
	
	private static final Logger logger = LogManager.getLogger(AppUserService.class);
	
	@Value("${password.key}")
	private String passwordKey;
	
	@Value("${password.salt}")
	private String salt;
	
	@Value("${password.iv}")
	private String passwordIv;

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private UserProfileConverter converter;
	
	@Autowired
	private ApplicationHistoryService historyService;
	
	@Autowired
	private PasswordUtil passwordUtil;
	
	
	public Boolean profileExiastById(Long id) {
		return userProfileRepository.existsById(id);
	}
	
	public UserProfile getProfileById(Long id) {
		return userProfileRepository.getProfileById(id).get();
	}
	
	public UserProfile saveProfile(Long id, UserProfileModel profileModel) {
		profileModel.setUserId(id);
		UserProfile profileEntity = userProfileRepository.save(converter.toEntity(profileModel));
		
		// Create History Record
		historyService.createHistoryRecord(
			new ApplicationHistory("user_profile", null, profileEntity.getUserId(), id, ChangeType.CREATE,
				null, null, "AdminUser"));
		
		return profileEntity;
	}

	public UserProfile updateProfile(Long id, UserProfileModel profileModel) {
		UserProfile profile = userProfileRepository.getReferenceById(id);
		
		if(profileModel.getFirstName() != null && !profile.getFirstName().equals(profileModel.getFirstName())) {
			// Create History Record
			historyService.createHistoryRecord(
				new ApplicationHistory("user_profile", "first_name", profileModel.getUserId(), id,
						ChangeType.UPDATE, profileModel.getFirstName(), profile.getFirstName(), "AdminUser"));
			profile.setFirstName(profileModel.getFirstName());
		}
		
		if(profileModel.getLastName() != null && !profile.getLastName().equals(profileModel.getLastName())) {
			// Create History Record
			historyService.createHistoryRecord(
					new ApplicationHistory("user_profile", "last_name", profileModel.getUserId(), id,
							ChangeType.UPDATE, profileModel.getLastName(), profile.getLastName(), "AdminUser"));
			profile.setLastName(profileModel.getLastName());
		}
		
		if(profileModel.getEmaill() != null && profile.getEmaill().equals(profileModel.getEmaill())) {
			// Create History Record
			historyService.createHistoryRecord(
					new ApplicationHistory("user_profile", "email", profileModel.getUserId(), id,
							ChangeType.UPDATE, profileModel.getEmaill(), profile.getEmaill(), "AdminUser"));
			profile.setEmaill(profileModel.getEmaill());
		}
		
		if(profileModel.getBirthDate() != null && !profile.getBirthDate().equals(profileModel.getBirthDate())) {
			String pattern = "MM/dd/yyyy HH:mm:ss";
			DateFormat df = new SimpleDateFormat(pattern);
			
			String newDate = df.format(profileModel.getBirthDate());
			String oldDate = df.format(profile.getBirthDate());
			
			// Create History Record
			historyService.createHistoryRecord(
				new ApplicationHistory("user_profile", "birthdate", profileModel.getUserId(), id,
						ChangeType.UPDATE, newDate, oldDate, "AdminUser"));
			
			profile.setBirthDate(profileModel.getBirthDate());
		}
		
		if(profileModel.getSsn() != null && !profileModel.getSsn().equals("")) {
			
			if(profile.getSsn() != null && !profile.getSsn().equals("")) {
				// Decrypt old SSN
				String currentSSN = profile.getSsn();
				try {
					SecretKey key = passwordUtil.getKeyFromPassword(passwordKey, salt);
					IvParameterSpec iv = new IvParameterSpec(passwordIv.getBytes());
					currentSSN = passwordUtil.decrypt(currentSSN, key, iv);
				} catch (Exception e) {
					logger.error("----  Could not encrypt SSN  -----", e);
					throw new FieldValueInvalidException("SSN", "", "");
				}
				
				if(!currentSSN.equals(profileModel.getSsn())) {
					try {
						SecretKey key = passwordUtil.getKeyFromPassword(passwordKey, salt);
						IvParameterSpec iv = new IvParameterSpec(passwordIv.getBytes());
						String ssn = passwordUtil.encrypt(profileModel.getSsn(), key, iv);
						
						// Create History Record
						historyService.createHistoryRecord(
							new ApplicationHistory("user_profile", "ssn", profileModel.getUserId(),id, ChangeType.UPDATE,
								ssn, profile.getSsn(), "AdminUser"));
						
						profile.setSsn(ssn);
					} catch (Exception e) {
						logger.error("----  Could not encrypt SSN  -----", e);
						throw new FieldValueInvalidException("SSN", "", "");
					}
				}
			} else {
				try {
					SecretKey key = passwordUtil.getKeyFromPassword(passwordKey, salt);
					IvParameterSpec iv = new IvParameterSpec(passwordIv.getBytes());
					String ssn = passwordUtil.encrypt(profileModel.getSsn(), key, iv);
					
					// Create History Record
					historyService.createHistoryRecord(
						new ApplicationHistory("user_profile", "ssn", profileModel.getUserId(),id, ChangeType.UPDATE,
								ssn, profile.getSsn(), "AdminUser"));
					
					profile.setSsn(ssn);
				} catch (Exception e) {
					logger.error("----  Could not encrypt SSN  -----", e);
					throw new FieldValueInvalidException("SSN", "", "");
				}
			}
			
		} else {
			profile.setSsn(null);
			// Create History Record
			historyService.createHistoryRecord(
				new ApplicationHistory("user_profile", "ssn", profileModel.getUserId(),id, ChangeType.UPDATE,
						null, profile.getSsn(), "AdminUser"));
		}
		
		return userProfileRepository.save(profile);
	}

	public void deleteProfile(Long id) {
		if(userProfileRepository.existsById(id)) {
			Optional<UserProfile> profileOptional = userProfileRepository.getProfileById(id);
            profileOptional.ifPresent(userProfile -> userProfileRepository.delete(userProfile));
		}
	}
}
