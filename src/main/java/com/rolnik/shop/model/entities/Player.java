package com.rolnik.shop.model.entities;

import com.rolnik.shop.model.entities.base.SimpleEntityWithDatesAndAuditUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "players")
public class Player extends SimpleEntityWithDatesAndAuditUser {
    @Size(min = 3, max = 255)
    private String nickname;
}
