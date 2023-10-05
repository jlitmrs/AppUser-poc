package com.tmrs.poc.nextgen.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tmrs.poc.nextgen.jpa.entity.UserPreference;
import com.tmrs.poc.nextgen.model.UserPreferenceModel;


@Service
public class UserPreferenceConverter {
	
//	@Autowired
    private ModelMapper modelMapper = new ModelMapper();
	
	public UserPreference toEntity(UserPreferenceModel model) {
		return modelMapper.map(model, UserPreference.class);
	}
	
	public UserPreferenceModel toModel(UserPreference entity) {
		return modelMapper.map(entity, UserPreferenceModel.class);
	}
}
