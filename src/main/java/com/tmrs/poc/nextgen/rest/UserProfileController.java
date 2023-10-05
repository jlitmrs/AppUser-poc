package com.tmrs.poc.nextgen.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmrs.poc.nextgen.exception.UserDoesNotExistException;
import com.tmrs.poc.nextgen.jpa.entity.UserProfile;
import com.tmrs.poc.nextgen.model.UserProfileModel;
import com.tmrs.poc.nextgen.service.UserProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserProfileController", description = "Api's for UserProfile's")
@RestController
@RequestMapping("/usr-profile")
public class UserProfileController {
	
	@Autowired
	private UserProfileService userProfileService;
	
	// Logger
	private static final Logger logger = LogManager.getLogger(UserProfileController.class);
	
	@Operation(
		summary = "Retrieve a UserProfile by Id",
	    description = "Get a UserProfile object by specifying its id. The User Profile contain the users personal information.")
	@ApiResponses({
	    @ApiResponse(responseCode = "302", content = { @Content(schema = @Schema(implementation = UserProfile.class), mediaType = "application/json") }),
	    @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
	    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{id}")
	public ResponseEntity<UserProfile> getProfileById(@PathVariable(name="id", required=true) Long id) {
		if(userProfileService.profileExiastById(id)) {
			logger.info("-----  GET request for UserProfile by id[ "+id+" ] -----");
			UserProfile profile = userProfileService.getProfileById(id);
			
			if(profile == null) {
				profile = new UserProfile();
				logger.info("-----  UserProfile for id[ %s ] was not found.  -----".formatted(id));
			} else {
				logger.info("-----  UserProfile for id[ %s ] was  found.  -----".formatted(id));
			}
			
			return new ResponseEntity<UserProfile>(profile, HttpStatus.FOUND);
			
		} else {
			logger.info("-----  UserProfile for id[ %s ] was not found.  -----".formatted(id));
			throw new UserDoesNotExistException("UserProfile not found", null, id);
		}
	}
	
	@Operation(
			summary = "Save a UserProfile",
		    description = "save a UserProfile.")
		@ApiResponses({
		    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserProfile.class), mediaType = "application/json") }),
		    @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
		    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PutMapping("/save/{id}")
	public ResponseEntity<UserProfile> saveProfile(@PathVariable(name="id", required=true) Long id, @RequestBody UserProfileModel profileModel) {
		return new ResponseEntity<UserProfile>(userProfileService.saveProfile(id, profileModel), HttpStatus.CREATED);
	}

}
