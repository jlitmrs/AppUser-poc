package com.tmrs.poc.app.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tmrs.poc.app.jpa.entity.enumeration.ChangeType;

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
public class HistorySearchModel {
	private String tableName;
	private String columnName;
	private ChangeType changeType;
	private Date from;
	private Date to;
	private int page;
	private int pageSize;

	@JsonIgnore
	public Pageable getPageable() {
		return PageRequest.of(page, pageSize);
	}
}
