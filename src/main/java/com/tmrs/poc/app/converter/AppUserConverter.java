package com.tmrs.poc.app.converter;

import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmrs.poc.app.jpa.entity.AppUser;
import com.tmrs.poc.app.model.AppUserCreateModel;
import com.tmrs.poc.app.model.AppUserModel;
import com.tmrs.poc.app.model.AppUserSimpleModel;

@Service
public class AppUserConverter {

    private ModelMapper modelMapper = new ModelMapper();
	

    public AppUserModel toModel(AppUser entity) {
		AppUserModel model = modelMapper.map(entity, AppUserModel.class);
				
		return model;
	}
	
	public AppUser toEntity(AppUserModel model) {
		AppUser entity = modelMapper.map(model, AppUser.class);
		return entity;
	}
	
	
	public AppUser fromCreateModelInputoEntity(AppUserCreateModel model) {
		AppUser entity = modelMapper.map(model, AppUser.class);
		return entity;
	}
	
	public AppUser fromSimpleModel(AppUserSimpleModel model) {
		AppUser entity = modelMapper.map(model, AppUser.class);
		return entity;
	}
	
	public AppUserSimpleModel toSimpleModel(AppUser entity) {
		AppUserSimpleModel model = modelMapper.map(entity, AppUserSimpleModel.class);
		return model;
	}

}
