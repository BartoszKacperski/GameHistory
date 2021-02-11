package com.rolnik.shop.controllers;

import com.rolnik.shop.dtos.playerround.PlayerRoundShortDetails;
import com.rolnik.shop.model.entities.PlayerRound;
import com.rolnik.shop.services.PlayerRoundService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@AllArgsConstructor

@RestController
@RequestMapping("/playerRounds")
public class PlayerRoundController {
    private final PlayerRoundService playerRoundService;
    private final ModelMapper modelMapper;


    @PutMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public PlayerRoundShortDetails updatePoint(@PathVariable Long id, @RequestBody BigDecimal point) {
        return mapPlayerRound(playerRoundService.updatePoint(id, point));
    }

    public PlayerRoundShortDetails mapPlayerRound(PlayerRound playerRound) {
        return modelMapper.map(playerRound, PlayerRoundShortDetails.class);
    }
}
