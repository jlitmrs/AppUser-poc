package com.tmrs.poc.nextgen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UserPreferenceModel {
	@JsonIgnore
	private Long userId;
	private String preferenceKey;
	@EqualsAndHashCode.Exclude
	private String dataType;
	@EqualsAndHashCode.Exclude
	private String preferenceValue;
	@EqualsAndHashCode.Exclude
	private Boolean custom;
}
