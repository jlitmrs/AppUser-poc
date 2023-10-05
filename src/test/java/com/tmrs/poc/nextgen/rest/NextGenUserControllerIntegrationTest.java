package com.tmrs.poc.nextgen.rest;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.Ordered;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.tmrs.poc.nextgen.NextgenPocApplication;
import com.tmrs.poc.nextgen.model.NextGenUserCreateModel;
import com.tmrs.poc.nextgen.model.UserProfileModel;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NextgenPocApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = { NextGenUserControllerIntegrationTest.class, DependencyInjectionTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class NextGenUserControllerIntegrationTest implements TestExecutionListener, Ordered {

	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	
	private static final Logger logger = LogManager.getLogger(NextGenUserControllerIntegrationTest.class);

	
	@Test
	public void testGetUserOneSuccess() 
	{	
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/usr/1"),
                HttpMethod.GET, entity, String.class);
		
		assertTrue(response.getStatusCode() == HttpStatus.FOUND);
	}
	
	@Test
	public void testGetNotFoundUserFailure() {
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/usr/99"),
                HttpMethod.GET, entity, String.class);
		
		assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testGetUserNameSuccess() 
	{	
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/usr/by-username/lweyrich"),
                HttpMethod.GET, entity, String.class);
		
		assertTrue(response.getStatusCode() == HttpStatus.FOUND);
	}
	
	@Test
	public void testCreateUser() {
		NextGenUserCreateModel model = NextGenUserCreateModel.builder()
				.userName("nexttestuser").password("nexttestuser").isAdmin(false).active(true)
				.profile(UserProfileModel.builder().firstName("NextTest").lastName("User").ssn("111-11-1111").birthDate(new Date()).build()).build();
		
		HttpEntity<NextGenUserCreateModel> entity = new HttpEntity<NextGenUserCreateModel>(model);
		
		ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/usr/"),
                HttpMethod.POST, entity, String.class);
		
		assertTrue(response.getStatusCode() == HttpStatus.OK);
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
