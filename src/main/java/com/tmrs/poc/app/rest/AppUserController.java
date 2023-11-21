package com.tmrs.poc.app.rest;

import java.net.URI;
import java.util.List;

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
import com.tmrs.poc.app.model.AppUserCreateModel;
import com.tmrs.poc.app.model.AppUserModel;
import com.tmrs.poc.app.model.AppUserSimpleModel;
import com.tmrs.poc.app.model.AppUserUpdateModel;
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
		AppUserModel user = appUserService.getUserById(id);
		if(user != null) {
			logger.info("User "+user.getUserName()+" for id["+id+"] found.");
			return new ResponseEntity<AppUserModel>(user, HttpStatus.FOUND);
		} else {
			logger.info("User for id["+id+"] was not found.");
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
	@PostMapping()
	public ResponseEntity<AppUser> createUser(@Valid @RequestBody AppUserCreateModel model) {
		if(appUserService.userNameExists(model.getUserName())) {
			logger.info("-----  User with username [ %s ] already exists.  -----".formatted(model.getUserName()));
			throw new UserAlreadyExistException("User with user name [ %s ] already exists.".formatted(model.getUserName()), model.getUserName());
		}
		
		AppUser user =  appUserService.createUser(model);
		
		if(user != null) {
			logger.info("-----  Creating user with username [ %s ] and ID [ %s ]  -----".formatted(user.getUserName(), user.getUserId()));
		} else {
			logger.info("-----  User with username [ %s ] was not created.  -----".formatted(model.getUserName()));
			return new ResponseEntity<AppUser>(new AppUser(), HttpStatus.BAD_REQUEST);
		}		
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getUserId()).toUri();
		
		return ResponseEntity.status(HttpStatus.CREATED).location(location).build();
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
	public AppUser updateUser(@Valid @RequestBody AppUserUpdateModel model) {
		if(! appUserService.userExists(model.getUserId())) {
			logger.info("-----  User with ID [ %s ] does not exist  -----".formatted(model.getUserId()));
			throw new UserDoesNotExistException("User not found", null, model.getUserId());
		}
		
		AppUser user = appUserService.updateUser(model);
		logger.info("-----  Updating user with username [ %s ] and ID  %s ]  -----".formatted(user.getUserName(), user.getUserId()));
		return user;
	}
	
	 
	 @Operation(
		summary = "Deletes a appUsers in the database",
	    description = "deletes a appUsers in the database.")
	 @ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = AppUser.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@DeleteMapping()
	public void deleteUser(Long userId) {
		if(! appUserService.userExists(userId)) {
			logger.info("-----  User with ID [ %s ] does not exist.  Coul not delete.  -----".formatted(userId));
			throw new UserDoesNotExistException("User not found", null, userId);
		}
		
		logger.info("-----  DELETE request for id[ %s ]  -----".formatted(userId));
		appUserService.deleteUser(userId);
	}
	
}
