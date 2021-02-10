package com.rolnik.shop.dtos.game;

import com.rolnik.shop.model.entities.Round;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class GameDetailsResponse {
    private Long id;
    private LocalDateTime date;
    private List<Round> rounds;
}
