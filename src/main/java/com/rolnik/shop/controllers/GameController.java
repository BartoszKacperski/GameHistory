package com.rolnik.shop.controllers;

import com.rolnik.shop.dtos.game.GameCreateRequest;
import com.rolnik.shop.dtos.game.GameCreateResponse;
import com.rolnik.shop.dtos.game.GameDetailsResponse;
import com.rolnik.shop.dtos.game.GameShortDetails;
import com.rolnik.shop.dtos.round.RoundAddRequest;
import com.rolnik.shop.dtos.round.RoundAddResponse;
import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.Round;
import com.rolnik.shop.model.entities.User;
import com.rolnik.shop.security.UserAuthDetails;
import com.rolnik.shop.services.GameService;
import com.rolnik.shop.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public GameCreateResponse create(@RequestBody GameCreateRequest gameCreateRequest, @AuthenticationPrincipal UserAuthDetails userAuthDetails) {
        return mapGame(
                gameService.create(
                        mapGame(gameCreateRequest),
                        userAuthDetails.getUser()
                )
        );
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public GameDetailsResponse getById(@PathVariable Long id) {
        return mapGameDetailsResponse(gameService.getById(id));
    }

    @GetMapping(
            value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<GameShortDetails> getAll() {
        return mapGamesShortDetailsResponse(gameService.getAll());
    }

    @GetMapping(
            value = "/finished",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public List<GameShortDetails> getAllFinished() {
        return mapGamesShortDetailsResponse(gameService.getAllFinished());
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        gameService.delete(id);
    }

    @PutMapping(
            value = "/{id}/finish",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public GameShortDetails finish(@PathVariable Long id, @AuthenticationPrincipal UserAuthDetails userAuthDetails) {
        return mapGameShortDetailsResponse(gameService.finishGame(id, userAuthDetails.getUser()));
    }

    @PutMapping(
            value = "/{id}/addRound",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public RoundAddResponse addRound(@PathVariable Long id, @RequestBody RoundAddRequest roundAddRequest) {
        return mapRound(gameService.addRound(id, mapRound(roundAddRequest)));
    }

    @GetMapping(
            value = "currentGame",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GameDetailsResponse getCurrentGame(@AuthenticationPrincipal UserAuthDetails userAuthDetails) {
        return mapGameDetailsResponse(
                userService.getCurrentGame(userAuthDetails.getUser())
        );
    }


    private Game mapGame(GameCreateRequest gameCreateRequest) {
        return modelMapper.map(gameCreateRequest, Game.class);
    }

    private GameCreateResponse mapGame(Game game) {
        return modelMapper.map(game, GameCreateResponse.class);
    }

    private GameDetailsResponse mapGameDetailsResponse(Game game) {
        return modelMapper.map(game, GameDetailsResponse.class);
    }

    private GameShortDetails mapGameShortDetailsResponse(Game game) {
        return modelMapper.map(game, GameShortDetails.class);
    }

    private List<GameShortDetails> mapGamesShortDetailsResponse(List<Game> games) {
        return games.stream()
                .map(this::mapGameShortDetailsResponse)
                .collect(Collectors.toList());
    }

    private Round mapRound(RoundAddRequest roundAddRequest) {
        return modelMapper.map(roundAddRequest, Round.class);
    }

    private RoundAddResponse mapRound(Round round) {
        return modelMapper.map(round, RoundAddResponse.class);
    }
}
