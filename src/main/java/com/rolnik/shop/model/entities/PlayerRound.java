package com.rolnik.shop.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rolnik.shop.model.entities.base.SimpleEntityWithDatesAndAuditUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "player_rounds")
public class PlayerRound extends SimpleEntityWithDatesAndAuditUser {
    @Digits(integer=18, fraction=2)
    private BigDecimal point;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    @ManyToOne
    @JoinColumn(name = "round_id")
    @JsonBackReference
    private Round round;
}
