package com.tmrs.poc.app.jpa.entity;

import com.tmrs.poc.app.jpa.entity.enumeration.Priority;

import com.tmrs.poc.app.jpa.entity.enumeration.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="task_list")
public class TaskList {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="task_id", columnDefinition="INTEGER")
    private Integer taskId;

    @Enumerated(EnumType.STRING)
    @Column(name="status", length=12, nullable = false, columnDefinition="varchar(12) DEFAULT 'CREATED'")
    private Status status;

    @Column(name="task_name", length=120, nullable = false, columnDefinition="varchar(120)")
    private String taskName;

    @Temporal(TemporalType.DATE)
    @Column(name="due_date", columnDefinition="datetime")
    private Date dueDate;

    @Column(name="created_by", length=16, nullable = false, columnDefinition="varchar(16)")
    private String createdBy;

    @Column(name="task_owner", length=16, columnDefinition="varchar(16)")
    private String taskOwner;

    @Column(name="date_completed", length=16, columnDefinition="varchar(16)")
    private Date dateCompleted;

    @Enumerated(EnumType.STRING)
    @Column(name="priority", length=12, columnDefinition="varchar(12) DEFAULT 'NONE'")
    private Priority priority;

    @Column(name="active", columnDefinition="boolean default true")
    private Boolean active;
}
