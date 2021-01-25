package com.rolnik.shop.model;

import com.rolnik.shop.model.base.SimpleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity(name = "games")
public class Game extends SimpleEntity {
    private LocalDateTime date;
}
