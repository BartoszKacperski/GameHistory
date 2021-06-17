package com.rolnik.shop.model.entities.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class SimpleEntityWithDates extends SimpleEntity {
    @CreatedDate
    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "last_update_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdateDate;
}
