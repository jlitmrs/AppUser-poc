package com.tmrs.poc.nextgen.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tmrs.poc.nextgen.jpa.entity.PreferenceValueLookup;
import com.tmrs.poc.nextgen.model.PreferenceValueLookupModel;

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
