package com.tmrs.poc.nextgen.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tmrs.poc.nextgen.jpa.entity.UserProfile;
import com.tmrs.poc.nextgen.model.UserProfileModel;

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
