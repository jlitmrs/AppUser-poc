package com.tmrs.poc.app.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ApiSubError> errors = new ArrayList<ApiSubError>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(new ApiSubError(error.getObjectName(), error.getField(), error.getRejectedValue(),
					error.getDefaultMessage()));
		}

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(new ApiSubError(error.getObjectName(), error.getDefaultMessage()));
		}

		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), ex, errors);

		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex, error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public  ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Field validation error", ex, ex.getMessage());
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			apiError.getSubErrors().add(new ApiSubError(error.getObjectName(), error.getDefaultMessage()));
		}
	    
	    return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({UserDoesNotExistException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> userNotFoundException(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "User does not exist", ex, ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
		return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler({UserAlreadyExistException.class})
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<Object> userAlreadyExistsException(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "User Already Exists.", ex, ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	
	@ExceptionHandler({PreferenceValueNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> preferenceValueNotFoundException(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "PreferenceValueLookup not found.", ex, ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler({PreferenceKeyNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> preferenceKeyNotFoundException(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "PreferenceKeyLookup not found.", ex, ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}


	@ExceptionHandler({FieldValueInvalidException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> fieldValueInvalidException(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid Field Value.", ex, ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	

	@ExceptionHandler({LoginException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> loginException(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Login Failed.", ex, ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	
	@ExceptionHandler({UserNotCreatedException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> userNotCreatedException(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Use was not created.", ex, ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
//	
//	@ExceptionHandler({BadCredentialsException.class})
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	public ResponseEntity<Object> badCredentialsException(Exception ex, WebRequest request) {
//		ApiError apiError = new ApiError(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "Invalid API Key", ex, ex.getMessage());
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}

}
