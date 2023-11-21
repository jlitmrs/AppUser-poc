package com.tmrs.poc.app.jpa.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmrs.poc.app.jpa.entity.AppUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class CustomAppUserRepositoryImpl implements CustomAppUserRepository{
	
	@PersistenceContext
    private EntityManager entityManager;

	public List<AppUser> findUser(Boolean active, String firstName, String lastName, String userName) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AppUser> select = cb.createQuery(AppUser.class);
        Root<AppUser> root = select.from(AppUser.class);
        
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if(userName != null && !userName.isEmpty()) {
        	predicates.add(cb.like(root.get("user_name").as(String.class), "%"+userName+"%")); 
        }
        
        if(firstName != null && !firstName.isEmpty()) {
        	predicates.add(cb.like(root.get("first_name").as(String.class), "%"+firstName+"%"));
        }
        
        if(lastName != null && !lastName.isEmpty()) {
        	predicates.add(cb.like(root.get("last_name").as(String.class), "%"+lastName+"%"));
        }
        
        if(active != null){
        	predicates.add(cb.equal(root.get("active"), active));
        }
        
        select.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(select).getResultList();
	}

}
