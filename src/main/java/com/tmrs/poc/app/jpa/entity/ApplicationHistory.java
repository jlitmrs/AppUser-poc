package com.tmrs.poc.app.jpa.entity;

import java.util.Date;

import com.tmrs.poc.app.jpa.entity.enumeration.ChangeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="application_history")
public class ApplicationHistory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="history_id", columnDefinition="INTEGER")
	private Long historyId;
	
	@Column(name="table_name", length=60, nullable=false, columnDefinition="VARCHAR(60)")
	private String tableName;
	
	@Column(name="column_name", length=60, columnDefinition="VARCHAR(60)")
	private String columnName;
	
	@Column(name="record_id", columnDefinition="INTEGER")
	private Long recordId;

	@Column(name="user_id", columnDefinition="INTEGER")
	private Long userId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="change_type", length=12, columnDefinition="VARCHAR(12)")
	private ChangeType changeType;
	
	@Column(name="old_value", length=256, columnDefinition="VARCHAR(256)")
	private String oldValue;
	
	@Column(name="new_value", length=256, columnDefinition="VARCHAR(256)")
	private String newValue;
	
	@Column(name="change_user", length=16, columnDefinition="VARCHAR(16)")
	private String changeUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name="change_date", columnDefinition="TIMESTAMP")
	private Date changeDate;
	
	public ApplicationHistory(String tableName, String columnName, Long recordId, Long userId, ChangeType changeType, String newValue, String oldValue, String changeUser) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.recordId = recordId;
		this.userId = userId;
		this.changeType = changeType;
		this.newValue = newValue;
		this.oldValue = oldValue;
		this.changeUser = changeUser;
	}
}
