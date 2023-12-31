package com.tmrs.poc.app.rest;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import com.tmrs.poc.app.jpa.entity.AppUser;
import com.tmrs.poc.app.model.AppUserModel;
import com.tmrs.poc.app.service.AppUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.Ordered;
import org.springframework.http.*;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.tmrs.poc.app.AppPocApplication;
import com.tmrs.poc.app.model.AppUserCreateModel;
import com.tmrs.poc.app.model.UserProfileModel;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AppPocApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = { AppUserControllerIntegrationTest.class, DependencyInjectionTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class AppUserControllerIntegrationTest implements TestExecutionListener, Ordered {

	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	@Autowired
	private AppUserService userService;
	
	private static final Logger logger = LogManager.getLogger(AppUserControllerIntegrationTest.class);
	
	@Test
	public void testGetUserOneSuccess() 
	{	
		HttpEntity<AppUserModel> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<AppUserModel> response = restTemplate.exchange(
                createURLWithPort("/api/usr/1"),
                HttpMethod.GET, entity, AppUserModel.class);
		
		assertTrue(response.getStatusCode() == HttpStatus.FOUND);
	}
	
	@Test
	public void testGetNotFoundUserFailure() {
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/usr/9999"),
                HttpMethod.GET, entity, String.class);
		
		assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testGetUserNameSuccess() 
	{	
		HttpEntity<AppUser> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<AppUser> response = restTemplate.exchange(
                createURLWithPort("/api/usr/by-username/lweyrich"),
                HttpMethod.GET, entity, AppUser.class);
		
		assertTrue(response.getStatusCode() == HttpStatus.FOUND);
	}
	
	@Test
	public void testCreateUser() {
		AppUserCreateModel model = AppUserCreateModel.builder()
				.userName("nexttestuser").password("nexttestuser").isAdmin(false).active(true).isUserEditor(false)
				.isAppUser(true).isUserViewer(true).profile(UserProfileModel.builder().firstName("NextTest")
						.lastName("User").ssn("121-11-1212").birthDate(new Date()).build()).build();
		
		HttpEntity<AppUserCreateModel> entity = new HttpEntity<AppUserCreateModel>(model);
		
		ResponseEntity<AppUserModel> response = restTemplate.exchange(
                createURLWithPort("/api/usr/create"),
                HttpMethod.POST, entity, AppUserModel.class);
		
		assertTrue("Expected 'CREATED' got " + response.getStatusCode(), response.getStatusCode().value() == 201);

		if(response.hasBody()) {
			userService.deleteUser(response.getBody().getUserId());
		}

		if(response.hasBody() && response.getBody().getUserId() != null) {
			userService.deleteUser(response.getBody().getUserId());
		}
	}
	
	private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
	
	public void beforeTestClass(TestContext testContext) throws Exception {
        logger.info("----- beforeTestClass : {}", testContext.getTestClass());
    }; 
    
    public void prepareTestInstance(TestContext testContext) throws Exception {
        logger.info("----- prepareTestInstance : {}", testContext.getTestClass());
    }; 
    
    public void beforeTestMethod(TestContext testContext) throws Exception {
        logger.info("----- beforeTestMethod : {}", testContext.getTestMethod());
    }; 
    
    public void afterTestMethod(TestContext testContext) throws Exception {
        logger.info("----- afterTestMethod : {}", testContext.getTestMethod());
    }; 
    
    public void afterTestClass(TestContext testContext) throws Exception {
        logger.info("----- afterTestClass : {}", testContext.getTestClass());
    }

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}


}
