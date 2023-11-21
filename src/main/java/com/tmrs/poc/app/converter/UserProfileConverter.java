package com.tmrs.poc.app.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tmrs.poc.app.jpa.entity.UserProfile;
import com.tmrs.poc.app.model.UserProfileModel;

@Service
public class UserProfileConverter {
	
//	@Autowired
    private ModelMapper modelMapper = new ModelMapper();
	
	public UserProfileModel toModel(UserProfile entity) {
		return modelMapper.map(entity, UserProfileModel.class);
	}
	
	public UserProfile toEntity(UserProfileModel model) {
		return modelMapper.map(model, UserProfile.class);
	}
}
