package com.rolnik.shop.controllers;

import com.rolnik.shop.dtos.player.PlayerCreateRequest;
import com.rolnik.shop.dtos.player.PlayerResponse;
import com.rolnik.shop.dtos.player.PlayerUpdateRequest;
import com.rolnik.shop.model.entities.Player;
import com.rolnik.shop.services.PlayerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Player create(@RequestBody PlayerCreateRequest playerCreateRequest) {
        return playerService.create(mapPlayer(playerCreateRequest));
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public PlayerResponse getById(@PathVariable long id) {
        return mapPlayer(playerService.getById(id));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerResponse> getAll() {
        return mapPlayers(playerService.getAll());
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public Player update(@RequestBody PlayerUpdateRequest playerUpdateRequest) {
        return playerService.update(mapPlayer(playerUpdateRequest));
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id) {
        playerService.delete(id);
    }


    private Player mapPlayer(PlayerCreateRequest playerCreateRequest) {
        return modelMapper.map(playerCreateRequest, Player.class);
    }

    private PlayerResponse mapPlayer(Player player) {
        return modelMapper.map(player, PlayerResponse.class);
    }

    private List<PlayerResponse> mapPlayers(List<Player> players) {
        return players.stream()
                .map(this::mapPlayer)
                .collect(Collectors.toList());
    }

    private Player mapPlayer(PlayerUpdateRequest playerUpdateRequest) {
        return modelMapper.map(playerUpdateRequest, Player.class);
    }
}
