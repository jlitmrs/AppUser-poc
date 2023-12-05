package com.tmrs.poc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmrs.poc.app.jpa.entity.ApplicationHistory;
import com.tmrs.poc.app.jpa.repository.ApplicationHistoryRepository;
import com.tmrs.poc.app.jpa.repository.ApplicationHistorySearchRepository;
import com.tmrs.poc.app.model.HistorySearchModel;
import com.tmrs.poc.app.model.Page;

import java.util.List;

@Service
public class ApplicationHistoryService {

	@Autowired
	private ApplicationHistoryRepository<ApplicationHistory, Long> repository;
	
	@Autowired
	private ApplicationHistorySearchRepository searchRepository;
	
	
	public void createHistoryRecord(ApplicationHistory entity) {
		repository.save(entity);
	}

	public List<ApplicationHistory> getAllHistory() {
		return repository.findAll();
	}
	
	public Page<ApplicationHistory> searchHistory(HistorySearchModel model) {
		return searchRepository.findBySearch(
			model.getTableName(), 
			model.getColumnName(), 
			model.getChangeType(), 
			model.getFrom(), 
			model.getTo(), 
			model.getPageable()
		);
	}
	
}
