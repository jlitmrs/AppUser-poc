package com.tmrs.poc.app.jpa.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmrs.poc.app.jpa.entity.ApplicationHistory;
import com.tmrs.poc.app.jpa.entity.ChangeType;
import com.tmrs.poc.app.model.Page;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class ApplicationHistorySearchRepository
{

	@PersistenceContext
    private EntityManager entityManager;
	
	
	public Page<ApplicationHistory> findBySearch(String tableName, String columnName, ChangeType changeType, Date fromDate, Date toDate, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ApplicationHistory> select = cb.createQuery(ApplicationHistory.class);
		Root<ApplicationHistory> root = select.from(ApplicationHistory.class);
        
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if(tableName != null) {
        	predicates.add(cb.like(root.get("table_name").as(String.class), "%"+tableName+"%")); 
        }
        
        if(columnName != null) {
        	predicates.add(cb.like(root.get("column_name").as(String.class), "%"+columnName+"%")); 
        }
        
        if(changeType != null) {
        	predicates.add(cb.equal(root.get("change_type").as(String.class), changeType.name())); 
        }
        
        if(toDate != null || fromDate != null) {
        	if(toDate != null && fromDate != null) {
        		predicates.add(cb.between(root.get("change_date").as(Date.class), fromDate, toDate));
        	} else if(toDate != null) {
        		predicates.add(cb.between(root.get("change_date").as(Date.class), null , toDate));
        	} else if(fromDate != null) {
        		predicates.add(cb.between(root.get("change_date").as(Date.class), fromDate, new Date()));
        	}
        }
        
        select.where(predicates.toArray(new Predicate[0]));
        TypedQuery<ApplicationHistory> typedQuery = entityManager.createQuery(select);
        
        typedQuery.setFirstResult(pageable.getPageNumber() - 1);
        typedQuery.setMaxResults(pageable.getPageSize());
        List<ApplicationHistory> historyList = typedQuery.getResultList();
        Page <ApplicationHistory> page = new Page<ApplicationHistory>();
        page.setResults(historyList);
        page.setPage(pageable.getPageNumber());
        page.setPageSize(pageable.getPageSize());
		return page;
	}

}
