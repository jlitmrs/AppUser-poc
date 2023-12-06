package com.tmrs.poc.app.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmrs.poc.app.jpa.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	@Query("SELECT usr FROM AppUser usr WHERE usr.active = true")
	public List<AppUser> getAllActiveUsers();
	
	@Query("SELECT usr FROM AppUser usr WHERE usr.active = :active")
	public List<AppUser> getUsersByActiveStatus(@Param("active") Boolean active);
	
	@Query("SELECT usr FROM AppUser usr WHERE usr.userId = :id")
	public AppUser getUserById(@Param("id") Long id);
	
	@Query("SELECT usr FROM AppUser usr WHERE usr.userName = :userName")
	public AppUser getByUserName(@Param("userName") String userName);
	
	@Query("SELECT COUNT(usr.userName) FROM AppUser usr WHERE usr.userName = :userName")
	public Integer doesUsernameExist(@Param("userName") String username);

	@Query("SELECT au.passwordHash from AppUser au WHERE au.userId = :userId")
	public String getCurrentPasswordHash(@Param("userId") Long userId);

	@Modifying
	@Query("UPDATE AppUser au SET au.passwordHash = :newPassword WHERE au.userId = :userId")
	public void changePassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);
}
