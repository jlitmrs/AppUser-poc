package com.tmrs.poc.app.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmrs.poc.app.converter.PreferenceValueLookupConverter;
import com.tmrs.poc.app.exception.ApiError;
import com.tmrs.poc.app.exception.PreferenceValueNotFoundException;
import com.tmrs.poc.app.jpa.entity.PreferenceValueLookup;
import com.tmrs.poc.app.model.PreferenceValueLookupModel;
import com.tmrs.poc.app.model.PreferenceValueModel;
import com.tmrs.poc.app.service.PreferenceValueLookupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PreferenceValueLookupController", description = "Api's for PreferenceValueLookup entity.  Avalable preferences key.")
@RestController
@RequestMapping("/preference-value")
public class PreferenceValueLookupController {

	@Autowired
	private PreferenceValueLookupService service;
	
	@Autowired
	PreferenceValueLookupConverter converter;
	
	@Operation(summary = "Retrieve all PreferenceValueLookup Entities.", description = "Retrieve a list of PreferenceValueLookup Entities.")
	@ApiResponses({ 
		@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PreferenceValueLookup.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@GetMapping("/all")
	public ResponseEntity<List<PreferenceValueLookup>> getAll() {
		return new ResponseEntity<List<PreferenceValueLookup>>(service.getAll(), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Retrieve all PreferenceValueLookup Entities by key.", description = "Retrieve a list of PreferenceValueLookup Entities by key.")
	@ApiResponses({ 
		@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PreferenceValueModel.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@GetMapping("/{key}")
	public ResponseEntity<List<PreferenceValueModel>> getPreferenceValueByKey(@PathVariable(name="key", required=true) String key) {
		if(service.preferenceKeyValueExists(key)) {
		return new ResponseEntity<List<PreferenceValueModel>>(service.getValueListByKey(key), HttpStatus.OK);
		} else {
			throw new PreferenceValueNotFoundException(key);
		}
	}
	
	
	@Operation(summary = "Find one PreferenceValueLookup Entity by key and value.", description = "Find one PreferenceValueLookup Entity by key and value.")
	@ApiResponses({ 
		@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PreferenceValueLookup.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
	@GetMapping("/{key}/{value}")
	public ResponseEntity<PreferenceValueLookup> findOneByKeyAndValue(@PathVariable(name="key", required=true) String key, @PathVariable(name="value", required=true) String value) {
		Optional<PreferenceValueLookup> lookupOption = service.findByKeyAndValue(key, value);
		
		if(lookupOption.isPresent()) {
			return new ResponseEntity<PreferenceValueLookup>(lookupOption.get(), HttpStatus.OK);
		} else {
			throw new PreferenceValueNotFoundException(key, value);
		}
	}
	
	
	@Operation(summary = "Create new PreferenceValueLookup Entity.", description = "Create new PreferenceValueLookup Entity.")
	@ApiResponses({ 
		@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = PreferenceValueLookup.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })	
	@PostMapping()
	public ResponseEntity<PreferenceValueLookup> createNewPreferenceValue(@RequestBody PreferenceValueLookupModel model) {
		return new ResponseEntity<PreferenceValueLookup>(service.save(converter.toEntity(model)), HttpStatus.CREATED);
	}
	

}
