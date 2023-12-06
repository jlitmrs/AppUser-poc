package com.tmrs.poc.app.rest;

import java.net.URI;
import java.util.List;

import com.tmrs.poc.app.exception.ChangePasswordException;
import com.tmrs.poc.app.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tmrs.poc.app.exception.ApiError;
import com.tmrs.poc.app.exception.UserAlreadyExistException;
import com.tmrs.poc.app.exception.UserDoesNotExistException;
import com.tmrs.poc.app.jpa.entity.AppUser;
import com.tmrs.poc.app.service.AppUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "AppUserController", description = "Api's for appUsers.  AppUser object has login information.")
@Validated
@RestController
@RequestMapping("/usr")
public class AppUserController {
	
	// Logger
	private static final Logger logger = LogManager.getLogger(AppUserController.class);
	
	
	@Autowired
	private AppUserService appUserService;
	

	
	 @Operation(
		summary = "Retrieve a AppUser by Id",
	    description = "Get a AppUser object by specifying its id. It will bring back active or non-active users.")
	  @ApiResponses({
	      @ApiResponse(responseCode = "302", content = { @Content(schema = @Schema(implementation = AppUser.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@GetMapping("/{id}")
	public ResponseEntity<AppUserModel> getappUser(@PathVariable(name="id", required=true) Long id) {
		logger.info("GET request for id["+id+"]");
		if(appUserService.userExists(id)) {
			AppUserModel user = appUserService.getUserById(id);
			if (user != null) {
				logger.info("User " + user.getUserName() + " for id[" + id + "] found.");
				return new ResponseEntity<AppUserModel>(user, HttpStatus.FOUND);
			} else {
				logger.info("User for id[" + id + "] was not found.");
				throw new UserDoesNotExistException("User not found", null, id);
			}
		} else {
			logger.info("User for id[" + id + "] was not found.");
			throw new UserDoesNotExistException("User not found", null, id);
		}
		
	}
	
	 
	 @Operation(
		summary = "Retrieve a AppUser by UserName",
	    description = "Get a AppUser object by specifying the UserName. It will bring back active or non-active users.")
	 @ApiResponses({
	      @ApiResponse(responseCode = "302", content = { @Content(schema = @Schema(implementation = AppUser.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@GetMapping("/by-username/{userName}")
	public ResponseEntity<AppUser> getByUserName(@PathVariable(name="userName", required=true) String userName) {
		logger.info("GET request for userName["+userName+"]");
		AppUser user = appUserService.getByUserName(userName);
		
		if(user != null) {
			logger.info("User "+user.getUserName()+" for userName["+userName+"] was found.");
			return new ResponseEntity<AppUser>(user, HttpStatus.FOUND);
		} else {
			logger.info("User for userName["+userName+"] was not found.");
			throw new UserDoesNotExistException("User not found", userName, null);
		}
	}
	
	 
	 @Operation(
		summary = "Retrieves all appUsers in the database",
	    description = "A list of all the users in the database. It will bring back active or non-active users.")
	 @ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = AppUser.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@GetMapping("/all")
	public ResponseEntity<List<AppUserSimpleModel>> getAllUsers() {
		List<AppUserSimpleModel> users = appUserService.getAllUsers();
		logger.info("GET request for all users. Found["+users.size()+"]");
		return new ResponseEntity<List<AppUserSimpleModel>>(users, HttpStatus.OK);
	}
	
	 
	 @Operation(
		summary = "Retrieves all active appUsers in the database",
	    description = "A list of all active users in the database. It will bring back only active users.")
	 @ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = AppUser.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@GetMapping("/all-active")
	public ResponseEntity<List<AppUserSimpleModel>> getAllActiveUsers() {
		List<AppUserSimpleModel> users = appUserService.getAllActiveUsers();
		logger.info("GET request for all users. Found["+users.size()+"]");
		return new ResponseEntity<List<AppUserSimpleModel>>(users, HttpStatus.OK);
	}
	
	 
	 @Operation(
		summary = "Creates a new active appUsers in the database",
	    description = "Creates a new active appUsers in the database.")
	 @ApiResponses({
	      @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = AppUser.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@PostMapping("/create")
	public ResponseEntity<AppUserModel> createUser(@Valid @RequestBody AppUserCreateModel model) {
		if(appUserService.userNameExists(model.getUserName())) {
			logger.info("-----  User with username [ %s ] already exists.  -----".formatted(model.getUserName()));
			throw new UserAlreadyExistException("User with user name [ %s ] already exists.".formatted(model.getUserName()), model.getUserName());
		}
		
		AppUserModel user =  appUserService.createUser(model);
		
		if(user != null) {
			logger.info("-----  Creating user with username [ %s ] and ID [ %s ]  -----".formatted(user.getUserName(), user.getUserId()));
		} else {
			logger.info("-----  User with username [ %s ] was not created.  -----".formatted(model.getUserName()));
			return new ResponseEntity<AppUserModel>(new AppUserModel(), HttpStatus.BAD_REQUEST);
		}		
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getUserId()).toUri();
		
		return ResponseEntity.created(location).body(user);
		//return new ResponseEntity<AppUserModel> (user, HttpStatus.CREATED);
	}
	
	 
	 @Operation(
		summary = "Updates a appUsers in the database",
	    description = "Updates a appUsers in the database.")
	 @ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = AppUser.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@PatchMapping()
	public ResponseEntity<AppUserSimpleModel> updateUser(@Valid @RequestBody AppUserUpdateModel model) {
		if(! appUserService.userExists(model.getUserId())) {
			logger.info("-----  User with ID [ %s ] does not exist  -----".formatted(model.getUserId()));
			throw new UserDoesNotExistException("User not found", null, model.getUserId());
		}
		
		AppUserSimpleModel user = appUserService.updateUser(model);
		logger.info("-----  Updating user with username [ %s ] and ID  %s ]  -----".formatted(user.getUserName(), user.getUserId()));
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	 
	 @Operation(
		summary = "Sets the AppUser activity in the database.",
	    description = "Sets the AppUser activity in the database.")
	 @ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@PatchMapping("activity/{userId}/{active}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId, @PathVariable("active") Boolean active) {
		if(! appUserService.userExists(userId)) {
			logger.info("-----  User with ID [ %s ] does not exist.  Could not delete.  -----".formatted(userId));
			throw new UserDoesNotExistException("User not found", null, userId);
		}
		
		logger.info("-----  Changed activity request for id[ %s ] to active[ %s ] -----".formatted(userId, active));
		appUserService.setUserActivity(userId, active);
		return ResponseEntity.status(HttpStatus.OK).body("User id["+userId+"] is active["+active+"]");
	}


	@Operation(
			summary = "Change users password.",
			description = "Change users password.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }),
			@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@PatchMapping("/change-password")
	public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordModel model) {
		logger.info("Password Change request by userId[ %s ]", model.getUserId());
		boolean passwordChange = appUserService.changePassword(model);


		if(passwordChange) {
			logger.info("Password changed for userId[ %s ] was successful", model.getUserId());
			return new ResponseEntity<String>("Password was successfully changed", HttpStatus.OK);
		} else {
			logger.info("Password changed for userId[ %s ] failed", model.getUserId());
			return new ResponseEntity<String>("Password was NOT changed", HttpStatus.BAD_REQUEST);
		}
	}
	
}
