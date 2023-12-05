package com.tmrs.poc.app.model;

import com.tmrs.poc.app.jpa.entity.enumeration.ChangeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationHistoryModel {
    private Long historyId;
    private String tableName;
    private String columnName;
    private Long recordId;
    private Long userId;
    private ChangeType changeType;
    private String oldValue;
    private String newValue;
    private String changeUser;
    private Date changeDate;
}
