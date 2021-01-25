package com.rolnik.shop.model;

import com.rolnik.shop.model.base.SimpleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "games")
public class Game extends SimpleEntity {
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;
}
