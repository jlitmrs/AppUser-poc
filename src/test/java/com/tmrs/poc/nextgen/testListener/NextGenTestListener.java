package com.tmrs.poc.nextgen.testListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;


public class NextGenTestListener implements TestExecutionListener, Ordered  {
	
	
	private static final Logger logger = LogManager.getLogger(NextGenTestListener.class);
	
	
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
