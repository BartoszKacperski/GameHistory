package com.rolnik.shop.dtos.round;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rolnik.shop.dtos.playerround.PlayerRoundShortDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoundAddResponse {
    private Long id;
    private List<PlayerRoundShortDetails> playerRounds;
}
