package com.tmrs.poc.app.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmrs.poc.app.jpa.entity.ApplicationHistory;

public interface ApplicationHistoryRepository <object,id> extends JpaRepository<ApplicationHistory, Long> {
	
}
