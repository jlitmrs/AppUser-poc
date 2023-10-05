package com.tmrs.poc.nextgen.jpa.entity;

import com.tmrs.poc.nextgen.jpa.entity.id.PreferenceValueId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name="preference_value_lookup")
@IdClass(PreferenceValueId.class)
public class PreferenceValueLookup {
	@Id
	@Column(name="preference_key", length=36, columnDefinition="VARCHAR(36)")
	private String preferenceKey;
	
	@Id
	@Column(name="preference_value", nullable=false, length=256, columnDefinition="VARCHAR(256)")
	private String value;
	
	@Column(name="data_type", nullable=false, length=16, columnDefinition="VARCHAR(16) DEFAULT 'String'")
	private String dataType;
	
	@Column(name="description", length=256, columnDefinition="varchar(256)")
	private String description;
}
