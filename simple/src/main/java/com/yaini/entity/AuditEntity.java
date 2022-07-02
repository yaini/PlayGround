package com.yaini.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreRemove;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditEntity {

    @CreatedDate
    @Column
    protected LocalDateTime createDate;

    @LastModifiedDate
    @Column
    protected LocalDateTime updateDate;

    @Column(nullable = false)
    protected Boolean deleted = Boolean.FALSE;

    @PreRemove
    public void deleteUser() {
        this.deleted = false;
    }
}
