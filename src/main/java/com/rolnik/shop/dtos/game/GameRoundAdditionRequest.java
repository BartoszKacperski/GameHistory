package com.rolnik.shop.dtos.game;

import com.rolnik.shop.model.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class GameRoundAdditionRequest {
    private Player player;
    private BigDecimal point;
}
