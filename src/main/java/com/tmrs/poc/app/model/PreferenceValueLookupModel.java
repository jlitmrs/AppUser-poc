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
public class PreferenceValueLookupModel {
	private String preferenceKey;
	private String value;
	private String dataType;
	private String description;
}
