package com.tmrs.poc.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class AppPocApplication implements ApplicationRunner{
	
	private static final Logger logger = LogManager.getLogger(AppPocApplication.class);

	public static void main(String[] args) {
		logger.info("-----  NextGen Application Starting...  -----");
		SpringApplication.run(AppPocApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
        logger.info("-----  NextGen Application started...  -----");
	}

}
