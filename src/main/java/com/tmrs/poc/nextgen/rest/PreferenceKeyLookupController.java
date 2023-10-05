package com.tmrs.poc.nextgen.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmrs.poc.nextgen.converter.PreferenceKeyLookupConverter;
import com.tmrs.poc.nextgen.exception.ApiError;
import com.tmrs.poc.nextgen.exception.PreferenceKeyNotFoundException;
import com.tmrs.poc.nextgen.jpa.entity.PreferenceKeyLookup;
import com.tmrs.poc.nextgen.model.PreferenceKeyLookupModel;
import com.tmrs.poc.nextgen.service.PreferenceKeyLookupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PreferenceKeyLookupController", description = "Api's for PreferenceKeyLookup entity.  Avalable preferences key.")
@RestController
@RequestMapping("/preference-key")
public class PreferenceKeyLookupController {

	@Autowired
	private PreferenceKeyLookupService preferenceKeyLookupService;
	
	@Autowired
	private PreferenceKeyLookupConverter converter;

	@Operation(summary = "Retrieve all PreferenceKeyLookup Entities.", description = "Retrieve a list of PreferenceKeyLookup Entities.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PreferenceKeyLookup.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@GetMapping("/all")
	public ResponseEntity<List<PreferenceKeyLookup>> getAll() {
		return new ResponseEntity<List<PreferenceKeyLookup>>(preferenceKeyLookupService.getAll(), HttpStatus.OK);
	}
	
	@Operation(summary = "Retrieve One PreferenceKeyLookup Entity by id.", description = "Retrieve One PreferenceKeyLookup Entity by id.")
	@ApiResponses({ 
		@ApiResponse(responseCode = "302", content = {@Content(schema = @Schema(implementation = PreferenceKeyLookup.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@GetMapping("/{key}")
	public ResponseEntity<PreferenceKeyLookup> getPreferenceKeyById(@PathVariable("key") String key) {
		if(preferenceKeyLookupService.preferenceKeyExists(key)) {
		return new ResponseEntity<PreferenceKeyLookup>(preferenceKeyLookupService.findByKey(key), HttpStatus.FOUND);
		} else {
			throw new PreferenceKeyNotFoundException(key);
		}
	}
	
	
	@Operation(summary = "Create a new PreferenceKeyLookup Entity.", description = "Create a new PreferenceKeyLookup Entity.")
	@ApiResponses({ 
		@ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = PreferenceKeyLookup.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@PostMapping
	public ResponseEntity<PreferenceKeyLookup> createPreferenceKey(@RequestBody PreferenceKeyLookupModel model) {
		return new ResponseEntity<PreferenceKeyLookup>(preferenceKeyLookupService.save(converter.toEntity(model)), HttpStatus.CREATED);
	}
	
	
	@Operation(summary = "Update a PreferenceKeyLookup Entity.", description = "Update a PreferenceKeyLookup Entity.")
	@ApiResponses({ 
		@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PreferenceKeyLookup.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@PutMapping
	public ResponseEntity<PreferenceKeyLookup> updatePreferenceKey(@RequestBody PreferenceKeyLookupModel model) {
		return new ResponseEntity<PreferenceKeyLookup>(preferenceKeyLookupService.save(converter.toEntity(model)), HttpStatus.OK);
	}
	
}
