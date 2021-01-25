package com.rolnik.shop.model;

import com.rolnik.shop.model.base.SimpleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity(name ="rounds")
public class Round extends SimpleEntity {
    private BigDecimal point;
    @OneToOne
    private Player player;
    @OneToOne
    private Game game;
}
