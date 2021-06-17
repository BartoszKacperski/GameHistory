package com.rolnik.shop.model.entities.base;

import com.rolnik.shop.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data

@MappedSuperclass
public class SimpleEntityWithDatesAndAuditUser extends SimpleEntityWithDates {
    @CreatedBy
    @Column(name = "created_by")
    private User createdBy;
    @LastModifiedBy
    @Column(name = "last_updated_by")
    private User lastUpdatedBy;
}
