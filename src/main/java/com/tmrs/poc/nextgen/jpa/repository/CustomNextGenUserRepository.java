package com.tmrs.poc.nextgen.jpa.repository;

import java.util.List;

import com.tmrs.poc.nextgen.jpa.entity.NextGenUser;

public interface CustomNextGenUserRepository {
	public List<NextGenUser> findUser(Boolean active, String firstName, String lastName, String userName);
}
