package com.tmrs.poc.app.jpa.repository;

import java.util.List;

import com.tmrs.poc.app.jpa.entity.AppUser;

public interface CustomAppUserRepository {
	public List<AppUser> findUser(Boolean active, String firstName, String lastName, String userName);
}
