package com.tmrs.poc.app.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tmrs.poc.app.jpa.entity.PreferenceKeyLookup;
import com.tmrs.poc.app.model.PreferenceKeyLookupModel;

@Service
public class PreferenceKeyLookupConverter {
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public PreferenceKeyLookupModel toModel(PreferenceKeyLookup entity) {
		return modelMapper.map(entity, PreferenceKeyLookupModel.class);
	}
	
	
	public PreferenceKeyLookup toEntity(PreferenceKeyLookupModel model) {
		return modelMapper.map(model, PreferenceKeyLookup.class);
	}
}
