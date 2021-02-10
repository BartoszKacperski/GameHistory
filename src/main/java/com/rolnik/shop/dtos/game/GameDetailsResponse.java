package com.rolnik.shop.dtos.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rolnik.shop.model.entities.Round;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class GameDetailsResponse {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private List<Round> rounds;
}
