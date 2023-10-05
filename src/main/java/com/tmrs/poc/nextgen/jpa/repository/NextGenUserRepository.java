package com.tmrs.poc.nextgen.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmrs.poc.nextgen.jpa.entity.NextGenUser;

public interface NextGenUserRepository extends JpaRepository<NextGenUser, Long> {

	@Query("SELECT usr FROM NextGenUser usr WHERE usr.active = true")
	public List<NextGenUser> getAllActiveUsers();
	
	@Query("SELECT usr FROM NextGenUser usr WHERE usr.active = :active")
	public List<NextGenUser> getUsersByActiveStatus(@Param("active") Boolean active);
	
	@Query("SELECT usr FROM NextGenUser usr WHERE usr.userId = :id")
	public NextGenUser getUserById(@Param("id") Long id);
	
	@Query("SELECT usr FROM NextGenUser usr WHERE usr.userName = :userName")
	public NextGenUser getByUserName(@Param("userName") String userName);
	
	@Query("SELECT COUNT(usr.userName) FROM NextGenUser usr WHERE usr.userName = :userName")
	public Integer doesUsernameExist(@Param("userName") String username);
}
