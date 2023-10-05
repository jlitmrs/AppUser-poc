package com.tmrs.poc.nextgen.service;

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

import com.tmrs.poc.nextgen.converter.NextGenUserConverter;
import com.tmrs.poc.nextgen.exception.FieldValueInvalidException;
import com.tmrs.poc.nextgen.exception.UserDoesNotExistException;
import com.tmrs.poc.nextgen.exception.UserNotCreatedException;
import com.tmrs.poc.nextgen.jpa.entity.NextGenUser;
import com.tmrs.poc.nextgen.jpa.entity.PreferenceKeyLookup;
import com.tmrs.poc.nextgen.jpa.entity.SecurityRole;
import com.tmrs.poc.nextgen.jpa.entity.UserPreference;
import com.tmrs.poc.nextgen.jpa.entity.UserProfile;
import com.tmrs.poc.nextgen.jpa.repository.CustomNextGenUserRepositoryImpl;
import com.tmrs.poc.nextgen.jpa.repository.NextGenUserRepository;
import com.tmrs.poc.nextgen.jpa.repository.SecurityRoleRepository;
import com.tmrs.poc.nextgen.model.NextGenUserCreateModel;
import com.tmrs.poc.nextgen.model.NextGenUserUpdateModel;
import com.tmrs.poc.nextgen.model.UserProfileModel;
import com.tmrs.poc.nextgen.util.PasswordUtil;

@Service
@Transactional
public class NextGenUserService {
	
	private static final Logger logger = LogManager.getLogger(NextGenUserService.class);
	
	@Value("${password.key}")
	private String passwordKey;
	
	@Value("${password.salt}")
	private String salt;
	
	@Value("${password.iv}")
	private String passwordIv;
	
	@Autowired
	private SecurityRoleRepository securityRoleRepository;
	
	@Autowired
	private NextGenUserRepository nextGenUserRepository;
	
	@Autowired
	private CustomNextGenUserRepositoryImpl customNextGenUserRepository;
	
	@Autowired
	private NextGenUserConverter nextGenUserConverter;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private UserPreferenceService userPreferenceService;
	
	@Autowired
	private PreferenceKeyLookupService prefKeyLookupService;
	
	@Autowired
	private PasswordUtil passwordUtil;

	
	
	public List<NextGenUser> getAllUsers() {
		List<NextGenUser> entityList = nextGenUserRepository.findAll();
		return entityList;
	}
	
	
	public List<NextGenUser> getAllActiveUsers() {
		return nextGenUserRepository.getAllActiveUsers();
	}
	
	
	public NextGenUser setUserActivity(Long userId, Boolean activity) {
		if(nextGenUserRepository.existsById(userId)) {
			NextGenUser user = nextGenUserRepository.getUserById(userId);
			if(user.getActive() != activity) {
				user.setActive(activity);
			}
			return user;
		} else {
			logger.info("User with user name [ %s ] does not exist.".formatted(userId));
			throw new UserDoesNotExistException("User with username [ %s ] does not exist.", null, userId);
		}
	}
	
	
	public NextGenUser getUserById(Long id) {
		return nextGenUserRepository.getUserById(id);
	}
	
	
	public NextGenUser getByUserName(String userName) {
		return nextGenUserRepository.getByUserName(userName);
	}
	

	public void deleteUser(Long id) {
		nextGenUserRepository.deleteById(id);
	}
	
	
	public NextGenUser createUser(NextGenUserCreateModel model) {
		NextGenUser user = nextGenUserConverter.fromCreateModelInputoEntity(model);
		
		if(model.getPassword() != null) {
			try {
				SecretKey key = passwordUtil.getKeyFromPassword(passwordKey, salt);
				IvParameterSpec iv = new IvParameterSpec(passwordIv.getBytes());
				String encryptedPassword = passwordUtil.encrypt(model.getPassword(), key, iv);
				user.setPasswordHash(encryptedPassword);
			} catch (Exception e) {
				logger.error("----  User not created  -----", e);
				throw new UserNotCreatedException(user.getUserName(), e.getMessage(), e);
			}
		} else {
			throw new FieldValueInvalidException("Password", "", "");
		}
		
		user = nextGenUserRepository.save(user);
		
		
		
		Set <SecurityRole> roleSet = new HashSet<SecurityRole>();
		
		
		if(model.getIsAdmin()) {
			Optional<SecurityRole> roleOption = securityRoleRepository.findById(1l);
			
			if(roleOption.isPresent()) {
				roleSet.add(roleOption.get());
			} else {
				logger.info("Role ADMINISTRATOR does not exist");
			}
		}
		
		if(model.getIsUserEditor()) {
			Optional<SecurityRole> roleOption = securityRoleRepository.findById(2l);
			
			if(roleOption.isPresent()) {
				roleSet.add(roleOption.get());
			} else {
				logger.info("Role USER_EDITOR does not exist");
			}
		}
		
		if(model.getIsUserViewer()) {
			Optional<SecurityRole> roleOption = securityRoleRepository.findById(3l);
			if(roleOption.isPresent()) {
				roleSet.add(roleOption.get());
			} else {
				logger.info("Role USER_VIEWER does not exist");
			}
		}
		
		if(model.getIsAppUser()) {
			Optional<SecurityRole> roleOption = securityRoleRepository.findById(4l);
			
			if(roleOption.isPresent()) {
				roleSet.add(roleOption.get());
			} else {
				logger.info("Role APP_USER does not exist");
			}
		}
		
		user.setRoles(roleSet);
		
		if(model.getProfile() != null) {
			UserProfileModel profileModel = model.getProfile();
			UserProfile profile = userProfileService.saveProfile(user.getUserId(), profileModel);
			user.setProfile(profile);
		}
		
		List<PreferenceKeyLookup> prekeyList = prefKeyLookupService.getAll();
		for(PreferenceKeyLookup lookup : prekeyList){
			UserPreference userPref = new UserPreference();
			userPref.setUserId(user.getUserId());
			userPref.setPreferenceKey(lookup.getPreferenceKey());
			userPref.setPreferenceValue(lookup.getDefaultValue());
			userPref.setDataType(lookup.getDataType());
			userPref.setCustom(lookup.getCustom());
			
			userPreferenceService.saveUserPreference(userPref);
		}
		
		this.nextGenUserRepository.save(user);
		
		return user;
	}
	
	
	public List<NextGenUser> searchUsers(String userName, String firstName, String lastName, Boolean active){
		return customNextGenUserRepository.findUser(active, firstName, lastName, userName);
	}
	
	
	public NextGenUser updateUser(NextGenUserUpdateModel model) {
		NextGenUser user = nextGenUserRepository.getUserById(model.getUserId());
		
		if(user == null) {
			throw new UserDoesNotExistException("User not updated, due to User Not Found", null, model.getUserId());
		}

		if(model.getActive() != null && !user.getActive().equals(model.getActive())) {
			user.setActive(model.getActive());
		}
		
		if(model.getProfile() != null) {
			UserProfile profile = userProfileService.updateProfile(model.getUserId(), model.getProfile());
			user.setProfile(profile);
		}
		
		return nextGenUserRepository.save(user);
	}
	
	
	public Boolean userExists(Long id) {
		return nextGenUserRepository.existsById(id);
	}
	
	
	public Boolean userNameExists(String username) {
		return nextGenUserRepository.doesUsernameExist(username) > 0;
	}
	
}
