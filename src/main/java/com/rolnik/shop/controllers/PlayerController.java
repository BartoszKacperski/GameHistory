package com.rolnik.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rolnik.shop.dto.PlayerRequest;
import com.rolnik.shop.model.Player;
import com.rolnik.shop.services.PlayerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
public class PlayerController {
    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    @PostMapping(
            value = "/players",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Player create(@RequestBody PlayerRequest playerRequest) {
        return playerService.create(mapPlayer(playerRequest));
    }

    @GetMapping(
            value = "/players/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public Player getAll(@PathVariable long id) {
        return playerService.getById(id);
    }

    @GetMapping(
            value = "/players",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getAll() {
        return playerService.getAll();
    }

    @PutMapping(
            value = "/players",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public Player update(@RequestBody Player player) {
        return playerService.update(player);
    }

    @DeleteMapping(
            value = "/players/{id}"
    )
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id) {
        playerService.delete(id);
    }


    private Player mapPlayer(PlayerRequest playerRequest) {
        return modelMapper.map(playerRequest, Player.class);
    }

}
