package com.tmrs.poc.nextgen.rest;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmrs.poc.nextgen.exception.UserDoesNotExistException;
import com.tmrs.poc.nextgen.jpa.entity.UserPreference;
import com.tmrs.poc.nextgen.model.UserPreferenceModel;
import com.tmrs.poc.nextgen.service.UserPreferenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserPreferenceController", description = "Api's for UserPreference's")
@RestController
@RequestMapping("/usr-preference")
public class UserPrefereneceController {
	
	@Autowired
	private UserPreferenceService userPreferenceService;
	
	// Logger
	private static final Logger logger = LogManager.getLogger(UserProfileController.class);
	
	
	@Operation(
		summary = "Retrieve a list of UserPreferences by Id",
	    description = "Get a list of UserPreferences by Id. The User Preferences contain the users application preferences.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserPreference.class), mediaType = "application/json") }),
	    @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
	    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{id}")
	public ResponseEntity<List<UserPreference>> getAllUserPreferencesById(@PathVariable(name="id") Long id) {
		List<UserPreference> preferences = userPreferenceService.getAllUserPreferences(id);
		
		if(preferences == null) {
			logger.info("-----  UserPreferences with id["+id+"] does not exist  -----");
			throw new UserDoesNotExistException("UserPreferences with id["+id+"] does not exist", null, id);
		} else {
			return new ResponseEntity<List<UserPreference>>(preferences, HttpStatus.FOUND);
		}
	}
	
	
	@Operation(
			summary = "Retrieve a UserPreferences by userId and preferneceKey.",
		    description = "Retrieve a UserPreferences by userId and preferneceKey.")
		@ApiResponses({
		    @ApiResponse(responseCode = "302", content = { @Content(schema = @Schema(implementation = UserPreference.class), mediaType = "application/json") }),
		    @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
		    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{userId}/{prefKey}")
	public ResponseEntity<UserPreference> getUserPreference(@PathVariable(name="userId") Long userId, @PathVariable(name="prefKey") String prefKey) {
		Optional<UserPreference> pref = userPreferenceService.getUserPreference(userId, prefKey);
		
		if(pref.isPresent()) {
			return new ResponseEntity<UserPreference> (pref.get(), HttpStatus.FOUND);
		} else {
			logger.info("-----  UserPreference with userId["+userId+"] and key["+prefKey+"] does not exist  ------");
			throw new UserDoesNotExistException("UserPreference with userId["+userId+"] and key["+prefKey+"] does not exist", prefKey, userId);
		}
	}
	
	
	@Operation(
		summary = "Create new UserPreference for a NextGenUser",
	    description = "Create new UserPreference for a NextGenUser.")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UserPreference.class), mediaType = "application/json") }),
	    @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
	    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping("/{id}")
	public ResponseEntity<UserPreference> createUserPreference(@RequestBody UserPreferenceModel model) {
		UserPreference preference = userPreferenceService.addUserPreference(model);
		logger.info("-----  UserPreference for id["+preference.getUserId()+"] and key["+preference.getPreferenceKey()+"] was created.  -----");
		return new ResponseEntity<UserPreference>(preference, HttpStatus.CREATED);
	}

}
