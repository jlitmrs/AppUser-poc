package com.tmrs.poc.nextgen.converter;

import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmrs.poc.nextgen.jpa.entity.NextGenUser;
import com.tmrs.poc.nextgen.model.NextGenUserCreateModel;
import com.tmrs.poc.nextgen.model.NextGenUserModel;
import com.tmrs.poc.nextgen.model.NextGenUserSimpleModel;

@Service
public class NextGenUserConverter {

    private ModelMapper modelMapper = new ModelMapper();
	

    public NextGenUserModel toModel(NextGenUser entity) {
		NextGenUserModel model = modelMapper.map(entity, NextGenUserModel.class);
				
		return model;
	}
	
	public NextGenUser toEntity(NextGenUserModel model) {
		NextGenUser entity = modelMapper.map(model, NextGenUser.class);
		return entity;
	}
	
	
	public NextGenUser fromCreateModelInputoEntity(NextGenUserCreateModel model) {
		NextGenUser entity = modelMapper.map(model, NextGenUser.class);
		return entity;
	}
	
	public NextGenUser fromSimpleModel(NextGenUserSimpleModel model) {
		NextGenUser entity = modelMapper.map(model, NextGenUser.class);
		return entity;
	}
	
	public NextGenUserSimpleModel toSimpleModel(NextGenUser entity) {
		NextGenUserSimpleModel model = modelMapper.map(entity, NextGenUserSimpleModel.class);
		return model;
	}

}
