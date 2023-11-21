package com.tmrs.poc.app.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tmrs.poc.app.jpa.entity.PreferenceValueLookup;
import com.tmrs.poc.app.model.PreferenceValueLookupModel;

@Service
public class PreferenceValueLookupConverter {

	private ModelMapper modelMapper = new ModelMapper();
	
	public PreferenceValueLookup toEntity(PreferenceValueLookupModel model) {
		return modelMapper.map(model, PreferenceValueLookup.class);
	}
	
	
	public PreferenceValueLookupModel toModel(PreferenceValueLookup entity) {
		return modelMapper.map(entity, PreferenceValueLookupModel.class);
	}
}
