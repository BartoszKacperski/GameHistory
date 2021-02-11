package com.rolnik.shop.model.entities;

import com.rolnik.shop.model.entities.base.SimpleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "roles")
public class Role extends SimpleEntity {
    @Length(min = 3, max = 100)
    private String name;
    @Length(min = 3, max = 255)
    private String description;
}
