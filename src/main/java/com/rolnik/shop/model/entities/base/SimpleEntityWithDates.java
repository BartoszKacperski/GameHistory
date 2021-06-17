package com.rolnik.shop.model.entities.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data

@MappedSuperclass
public abstract class SimpleEntityWithDates extends SimpleEntity {
    @CreationTimestamp
    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "last_update_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdateDate;
}
