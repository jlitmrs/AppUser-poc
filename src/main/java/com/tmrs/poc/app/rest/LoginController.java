package com.tmrs.poc.app.rest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmrs.poc.app.exception.ApiError;
import com.tmrs.poc.app.exception.LoginException;
import com.tmrs.poc.app.jpa.entity.AppUser;
import com.tmrs.poc.app.jpa.entity.SecurityPermission;
import com.tmrs.poc.app.jpa.entity.UserPreference;
import com.tmrs.poc.app.model.LoginModel;
import com.tmrs.poc.app.model.LoginUserModel;
import com.tmrs.poc.app.service.AppUserService;
import com.tmrs.poc.app.util.PasswordUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "LoginController", description = "Api's for Logging in.")
@Validated
@RestController
@RequestMapping("/authentication")
public class LoginController {
	
	// Logger
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private PasswordUtil passwordUtil;
	
	@Value("${password.key}")
	private String passwordKey;
	
	@Value("${password.salt}")
	private String salt;
	
	@Value("${password.iv}")
	private String passwordIv;
	
	
	@Operation(
			summary = "Login to Application AppPoc",
			description = "Login to Application appPoc.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = LoginUserModel.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@PostMapping("/login")
	public ResponseEntity<LoginUserModel> login(@Valid @RequestBody LoginModel model) {
		if(appUserService.userNameExists(model.getUsername())) {
			AppUser user = appUserService.getByUserName(model.getUsername());
			
			if(user == null ) {
				logger.error(String.format("-----  Login failed for user [ %s ].  User not found.  -----", model.getUsername()));
				throw new LoginException("Login failed for user [ %s ].", model.getUsername(), null, null);
			}
			
			if(user.getActive() == false) {
				logger.error(String.format("-----  Login failed for user [ %s ].  User is not active.  -----", model.getUsername()));
				throw new LoginException("Login failed for user [ %s ].  User is not active.", model.getUsername(), null, null);
			}
			
			SecretKey key;
			try {
				key = passwordUtil.getKeyFromPassword(passwordKey, salt);
				IvParameterSpec iv = new IvParameterSpec(passwordIv.getBytes());
			
				if(passwordUtil.verifyUserPassword(model.getPassword().strip(), user.getPasswordHash().strip(), key, iv)) {
					
					Set<String> roleSet = new HashSet<String>();
					Set<String> permissionSet = new HashSet<String>();
					user.getRoles().stream().forEach(role -> {
						
						for(SecurityPermission permission : role.getPermissions()) {
							permissionSet.add(permission.getPermissionName());
						}
						roleSet.add(role.getRoleName());
					});
					
					List <UserPreference> prefList = user.getUserPreferences();
					Map <String, String> prefModelMap = new HashMap <String, String> ();
								
					prefList.stream().forEach(preference -> {
						prefModelMap.put(preference.getPreferenceKey(), preference.getPreferenceValue());
					});
					
					
					LoginUserModel loginUser = LoginUserModel.builder()
							.userId(user.getUserId())
							.userName(user.getUserName())
							.firstName(user.getProfile().getFirstName())
							.lastName(user.getProfile().getLastName())
							.email(user.getProfile().getEmaill())
							.permissions(permissionSet)
							.roles(roleSet)
							.userPreferences(prefModelMap)
							.build();
					return new ResponseEntity<LoginUserModel>(loginUser, HttpStatus.OK);
				} else {
					logger.error(String.format("-----  Login Failed for username [ %s ] salt [ %s ].  Password.", model.getUsername(), salt));
					throw new LoginException("Login failed for user [ %s ] ", user.getUserName(), model.getPassword(), salt);
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				logger.error(e.getMessage(), e);
				throw new LoginException("Login failed for user [ %s ].", user.getUserName(), model.getPassword(), salt);
			}
		} else {
			logger.error(String.format("Login Failed for username [ %s ].  Bad UserName.", model.getUsername()));
			throw new LoginException("Login failed for user [ %s ].  Bad UserName or Password", model.getUsername(), null, null);
		}
		
	}
	
	@Operation(
			summary = "Log off Application appPoc",
			description = "Log off Application appPoc.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }),
		@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@GetMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("userName") String userName) {
		if(appUserService.userNameExists(userName)) {
			return new ResponseEntity<String>("[ %s ] you have been successfully logged out!".formatted(userName), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User [ %s ] was not found.  Logout unsuccessful.".formatted(userName), HttpStatus.NOT_FOUND);
		}
	}

}
