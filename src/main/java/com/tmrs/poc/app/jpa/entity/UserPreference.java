package com.tmrs.poc.app.jpa.entity;

import com.tmrs.poc.app.jpa.entity.id.PreferenceId;

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
@Table(name="user_preference")
@IdClass(PreferenceId.class)
public class UserPreference {
	
	@Id
	@Column(name="user_id", columnDefinition="BIGINT")
	private Long userId;
	
	@Id
	@Column(name="preference_key", unique=true, length=36, updatable=false, columnDefinition="VARCHAR(36)")
	private String preferenceKey;
	
	@EqualsAndHashCode.Exclude
	@Column(name="preference_value", length=256, columnDefinition="varchar(256)")
	private String preferenceValue;

	@Column(name="data_type", length=16, columnDefinition="VARCHAR(16) DEFAULT 'String'")
	private String dataType;
	
	@EqualsAndHashCode.Exclude
	@Column(name="is_custom_value", columnDefinition="Boolean DEFAULT true")
	private Boolean custom;
}
