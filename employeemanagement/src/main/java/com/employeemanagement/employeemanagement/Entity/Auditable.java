package com.employeemanagement.employeemanagement.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> implements Serializable {

     @CreatedDate
     @Column(updatable = false)
    private LocalDateTime createdTime;
     @LastModifiedDate
    private LocalDateTime updatedTime;
     @CreatedBy
    private String createdBy;
     @LastModifiedBy
    private String updatedBy;
    private boolean isdelete=false;
}
