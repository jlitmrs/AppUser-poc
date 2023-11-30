package com.tmrs.poc.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmrs.poc.app.converter.AppUserConverter;
import com.tmrs.poc.app.exception.FieldValueInvalidException;
import com.tmrs.poc.app.exception.UserDoesNotExistException;
import com.tmrs.poc.app.exception.UserNotCreatedException;
import com.tmrs.poc.app.jpa.entity.AppUser;
import com.tmrs.poc.app.jpa.entity.ApplicationHistory;
import com.tmrs.poc.app.jpa.entity.enumeration.ChangeType;
import com.tmrs.poc.app.jpa.entity.PreferenceKeyLookup;
import com.tmrs.poc.app.jpa.entity.SecurityRole;
import com.tmrs.poc.app.jpa.entity.UserPreference;
import com.tmrs.poc.app.jpa.entity.UserProfile;
import com.tmrs.poc.app.jpa.repository.AppUserRepository;
import com.tmrs.poc.app.jpa.repository.CustomAppUserRepositoryImpl;
import com.tmrs.poc.app.jpa.repository.SecurityRoleRepository;
import com.tmrs.poc.app.model.AppUserCreateModel;
import com.tmrs.poc.app.model.AppUserModel;
import com.tmrs.poc.app.model.AppUserSimpleModel;
import com.tmrs.poc.app.model.AppUserUpdateModel;
import com.tmrs.poc.app.model.UserProfileModel;
import com.tmrs.poc.app.util.PasswordUtil;

@Service
@Transactional
public class AppUserService {
	
	private static final Logger logger = LogManager.getLogger(AppUserService.class);
	
	@Value("${password.key}")
	private String passwordKey;
	
	@Value("${password.salt}")
	private String salt;
	
	@Value("${password.iv}")
	private String passwordIv;
	
	@Autowired
	private SecurityRoleRepository securityRoleRepository;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private CustomAppUserRepositoryImpl customAppUserRepository;
	
	@Autowired
	private AppUserConverter appUserConverter;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private UserPreferenceService userPreferenceService;
	
	@Autowired
	private PreferenceKeyLookupService prefKeyLookupService;
	
	@Autowired
	private ApplicationHistoryService historyService;
	
	@Autowired
	private PasswordUtil passwordUtil;

	
	
	public List<AppUserSimpleModel> getAllUsers() {
		List<AppUser> entityList = appUserRepository.findAll();
		List <AppUserSimpleModel> modelList = new ArrayList <AppUserSimpleModel> (entityList.size());
		
		for(AppUser entity : entityList) {
			modelList.add(appUserConverter.toSimpleModel(entity));
		}
		
		return modelList;
	}
	
	
	public List<AppUserSimpleModel> getAllActiveUsers() {
		List <AppUser> entityList = appUserRepository.getAllActiveUsers();
		List <AppUserSimpleModel> modelList = new ArrayList <AppUserSimpleModel> (entityList.size());
		
		for(AppUser entity : entityList) {
			modelList.add(appUserConverter.toSimpleModel(entity));
		}
		
		return modelList;
	}
	
	
	public AppUser setUserActivity(Long userId, Boolean activity) {
		if(appUserRepository.existsById(userId)) {
			AppUser user = appUserRepository.getUserById(userId);
			if(user.getActive() != activity) {
				user.setActive(activity);
				
				//create history record for change
				historyService.createHistoryRecord(
					new ApplicationHistory(
						"app_usr", "active", userId, userId, ChangeType.UPDATE,
						Boolean.valueOf(!activity.booleanValue()).toString(), activity.toString(), "AdminUser"));
			}
			return user;
		} else {
			logger.info("User with user name [ %s ] does not exist.".formatted(userId));
			throw new UserDoesNotExistException("User with username [ %s ] does not exist.", null, userId);
		}
	}
	
	
	public AppUserModel getUserById(Long id) {
		return appUserConverter.toModel(appUserRepository.getUserById(id));
	}
	
	
	public AppUser getByUserName(String userName) {
		return appUserRepository.getByUserName(userName);
	}
	

	public void deleteUser(Long id) {
		appUserRepository.deleteById(id);
		historyService.createHistoryRecord(
			new ApplicationHistory("app_usr", null, id, id, ChangeType.DELETE, null, null, "AdminUser"));
	}
	
	
	public AppUserModel createUser(AppUserCreateModel model) {
		AppUser user = appUserConverter.fromCreateModelInputoEntity(model);
		
		if(model.getPassword() != null) {
			try {
				SecretKey key = passwordUtil.getKeyFromPassword(passwordKey, salt);
				IvParameterSpec iv = new IvParameterSpec(passwordIv.getBytes());
				String encryptedPassword = passwordUtil.encrypt(model.getPassword(), key, iv);
				String ssn = passwordUtil.encrypt(model.getProfile().getSsn(), key, iv);
				user.setPasswordHash(encryptedPassword);
			} catch (Exception e) {
				logger.error("----  User not created  -----", e);
				throw new UserNotCreatedException(user.getUserName(), e.getMessage(), e);
			}
		} else {
			throw new FieldValueInvalidException("Password", "", "");
		}
		
		user = appUserRepository.save(user);
		
		historyService.createHistoryRecord(
			new ApplicationHistory("app_usr", null, user.getUserId(), user.getUserId(), ChangeType.CREATE, user.getUserName(), null, "AdminUser"));

		if(model.getProfile() != null) {

			UserProfileModel profileModel = model.getProfile();
			if(model.getProfile().getSsn() != null) {
				try {
					SecretKey key = passwordUtil.getKeyFromPassword(passwordKey, salt);
					IvParameterSpec iv = new IvParameterSpec(passwordIv.getBytes());
					String ssn = passwordUtil.encrypt(model.getProfile().getSsn(), key, iv);
					profileModel.setSsn(ssn);
				} catch (Exception e) {
					logger.error("----  Could not encrypt SSN  -----", e);
					throw new FieldValueInvalidException("SSN", "", "");
				}
			} else {
				throw new FieldValueInvalidException("SSN", "", "");
			}
			UserProfile profile = userProfileService.saveProfile(user.getUserId(), profileModel);
			user.setProfile(profile);
			historyService.createHistoryRecord(
					new ApplicationHistory("user_profile", null, user.getUserId(), user.getUserId(),
							ChangeType.CREATE, null, null, "AdminUser"));
		}

		Set <SecurityRole> roleSet = new HashSet<SecurityRole>();

		if(model.getIsAdmin()) {
			Optional<SecurityRole> roleOption = securityRoleRepository.findById(1l);

			if(roleOption.isPresent()) {
				roleSet.add(roleOption.get());
				historyService.createHistoryRecord(
						new ApplicationHistory("security_role", null, roleOption.get().getRoleId(),
								user.getUserId(), ChangeType.CREATE, roleOption.get().getRoleName(), null, "AdminUser"));
			} else {
				logger.info("Role ADMINISTRATOR does not exist");
			}
		}
		
		if(model.getIsUserEditor()) {
			Optional<SecurityRole> roleOption = securityRoleRepository.findById(2l);
			
			if(roleOption.isPresent()) {
				roleSet.add(roleOption.get());
				historyService.createHistoryRecord(
						new ApplicationHistory("security_role", null, roleOption.get().getRoleId(), user.getUserId(),
								ChangeType.CREATE, roleOption.get().getRoleName(), null, "AdminUser"));
			} else {
				logger.info("Role USER_EDITOR does not exist");
			}
		}
		
		if(model.getIsUserViewer()) {
			Optional<SecurityRole> roleOption = securityRoleRepository.findById(3l);
			if(roleOption.isPresent()) {
				roleSet.add(roleOption.get());
				historyService.createHistoryRecord(
						new ApplicationHistory("security_role", null, roleOption.get().getRoleId(), user.getUserId(),
								ChangeType.CREATE, roleOption.get().getRoleName(), null, "AdminUser"));
			} else {
				logger.info("Role USER_VIEWER does not exist");
			}
		}
		
		if(model.getIsAppUser()) {
			Optional<SecurityRole> roleOption = securityRoleRepository.findById(4l);
			
			if(roleOption.isPresent()) {
				roleSet.add(roleOption.get());
				historyService.createHistoryRecord(
						new ApplicationHistory("security_role", null, roleOption.get().getRoleId(), user.getUserId(),
								ChangeType.CREATE, roleOption.get().getRoleName(), null, "AdminUser"));
			} else {
				logger.info("Role APP_USER does not exist");
			}
		}
		
		user.setRoles(roleSet);
		
		List<PreferenceKeyLookup> prekeyList = prefKeyLookupService.getAll();
		for(PreferenceKeyLookup lookup : prekeyList){
			UserPreference userPref = new UserPreference();
			userPref.setUserId(user.getUserId());
			userPref.setPreferenceKey(lookup.getPreferenceKey());
			userPref.setPreferenceValue(lookup.getDefaultValue());
			userPref.setDataType(lookup.getDataType());
			userPref.setCustom(lookup.getCustom());
			
			userPreferenceService.saveUserPreference(userPref);
			historyService.createHistoryRecord(
				new ApplicationHistory("user_preference", null, user.getUserId(), user.getUserId(), ChangeType.CREATE,
					lookup.getPreferenceKey(), null, "AdminUser"));
		}
		
		user = this.appUserRepository.save(user);
		
		return appUserConverter.toModel(user);
	}
	
	
	public List<AppUser> searchUsers(String userName, String firstName, String lastName, Boolean active){
		return customAppUserRepository.findUser(active, firstName, lastName, userName);
	}
	
	
	public AppUser updateUser(AppUserUpdateModel model) {
		AppUser user = appUserRepository.getUserById(model.getUserId());
		
		if(user == null) {
			throw new UserDoesNotExistException("User not updated, due to User Not Found", null, model.getUserId());
		}

		if(model.getActive() != null && !user.getActive().equals(model.getActive())) {
			Boolean oldValue = user.getActive();
			user.setActive(model.getActive());
			historyService.createHistoryRecord(
				new ApplicationHistory("app_usr", "active", user.getUserId(), user.getUserId(),
						ChangeType.UPDATE, model.getActive().toString(), oldValue.toString(), "AdminUser"));
		}
		
		if(model.getProfile() != null) {
			UserProfile profile = userProfileService.updateProfile(model.getUserId(), model.getProfile());
			user.setProfile(profile);
		}
		
		return appUserRepository.save(user);
	}
	
	
	public Boolean userExists(Long id) {
		return appUserRepository.existsById(id);
	}
	
	
	public Boolean userNameExists(String username) {
		return appUserRepository.doesUsernameExist(username) > 0;
	}
	
}
