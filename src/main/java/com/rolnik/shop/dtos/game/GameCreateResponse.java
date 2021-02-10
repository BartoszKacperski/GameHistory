package com.rolnik.shop.dtos.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class GameCreateResponse {
    private Long id;
    private LocalDateTime date;
}
