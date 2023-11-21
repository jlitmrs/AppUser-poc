package com.tmrs.poc.app.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tmrs.poc.app.service.AppUserService;
import com.tmrs.poc.app.util.PasswordUtil;

@EnableTransactionManagement
@Configuration
public class DataSourceConfig {
	
	@Value("${database.driverClassName}")
	private String driver;
	
	@Value("${database.url}")
	private String url;
	
	@Value("${database.username}")
	private String userName;
	
	@Value("${database.password}")
	private String password;
	
	@Value("${password.salt}")
	private String salt;
	
	@Value("${database.pwk}")
	private String passwordKey;
	
	@Value("${database.pwiv}")
	private String passwordIv;
	
	@Autowired
	private PasswordUtil passwordUtil;
	
	private static final Logger logger = LogManager.getLogger(AppUserService.class);
	
	
	
	@Bean
	@Primary
	public DataSource dataSource() {
		DataSourceBuilder <?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(userName);
        try {
        	SecretKey key = passwordUtil.getKeyFromPassword(passwordKey, salt);
			IvParameterSpec iv = new IvParameterSpec(passwordIv.getBytes());
        	String decPassword = passwordUtil.decrypt(password, key, iv);
			dataSourceBuilder.password(decPassword);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
        return dataSourceBuilder.build();
	}

}
