package com.tmrs.poc.nextgen.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name="preference_key_lookup")
public class PreferenceKeyLookup {
	
	@Id
	@Column(name="preference_key", length=36, columnDefinition="VARCHAR(36)")
	private String preferenceKey;
	
	@Column(name="default_value", length=256, columnDefinition="VARCHAR(256)")
	private String defaultValue;
	
	@Column(name="data_type", length=16, columnDefinition="VARCHAR(16) DEFAULT 'String'")
	private String dataType;
	
	@EqualsAndHashCode.Exclude
	@Column(name="description", length=256, columnDefinition="VARCHAR(256)")
	private String description;
	
	@EqualsAndHashCode.Exclude
	@Column(name="is_custom_value", columnDefinition="Boolean DEFAULT true")
	private Boolean custom;
	
}
