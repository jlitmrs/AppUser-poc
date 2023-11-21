package com.tmrs.poc.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceKeyLookupModel {
private String preferenceKey;
	private String defaultValue;
	private String dataType;
	private Boolean custom;
	private String description;
}
