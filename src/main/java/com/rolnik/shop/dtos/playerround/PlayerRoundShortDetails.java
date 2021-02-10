package com.rolnik.shop.dtos.playerround;

import com.rolnik.shop.dtos.player.PlayerShortDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PlayerRoundShortDetails {
    private BigDecimal point;
    private PlayerShortDetails player;
}
