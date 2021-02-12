package com.rolnik.shop.controllers;

import com.rolnik.shop.dtos.game.GameCreateRequest;
import com.rolnik.shop.dtos.player.PlayerCreateRequest;
import com.rolnik.shop.dtos.round.RoundAddRequest;
import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.Player;
import com.rolnik.shop.model.entities.PlayerRound;
import com.rolnik.shop.model.entities.Round;
import com.rolnik.shop.services.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(GameController.class)
class GameControllerTest extends BaseControllerTest {
    @MockBean
    private GameService gameService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    void whenCreateAuthorized_thenReturnGameResponseJson() throws Exception {
        //given
        LocalDateTime gameDate = LocalDateTime.now();
        GameCreateRequest gameCreateRequest = new GameCreateRequest(
                gameDate
        );
        //when
        Mockito.when(gameService.create(Mockito.any())).thenAnswer(answer -> answer.getArgument(0));
        //then
        mvc.perform(post("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.toJson(gameCreateRequest))
                .header("Authorization", super.getAdminAuthToken()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.date", is(formatter.format(gameDate)))
                );
    }

    @Test
    void whenCreateNotAuthorized_thenStatusForbidden() throws Exception {
        //given
        GameCreateRequest gameCreateRequest = new GameCreateRequest(
                LocalDateTime.now()
        );
        //when
        Mockito.when(gameService.create(Mockito.any())).thenAnswer(answer -> answer.getArgument(0));
        //then
        mvc.perform(post("/games")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()
                );
    }

    @Test
    void whenGetByIdValid_thenReturnGameDetailResponseJson() throws Exception {
        //given
        LocalDateTime gameDate = LocalDateTime.now();
        Game game = super.createGame(
                gameDate,
                false,
                super.createRound(
                        super.createPlayerRound(
                                createPlayer("firstPlayer"),
                                BigDecimal.TEN
                        ),
                        super.createPlayerRound(
                                createPlayer("secondPlayer"),
                                BigDecimal.ONE
                        )
                )
        );
        //when
        Mockito.when(gameService.getById(1L)).thenReturn(game);
        //then
        mvc.perform(get("/games/1")
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is(formatter.format(gameDate))))
                .andExpect(jsonPath("$.finished", is(false)))
                .andExpect(jsonPath("$.rounds", hasSize(1)))
                .andExpect(jsonPath("$.rounds[0].playerRounds", hasSize(2)))
                .andExpect(jsonPath("$.rounds[0].playerRounds[0].player.nickname", is("firstPlayer")))
                .andExpect(jsonPath("$.rounds[0].playerRounds[1].player.nickname", is("secondPlayer")))
                .andExpect(jsonPath("$.rounds[0].playerRounds[0].point", is(10)))
                .andExpect(jsonPath("$.rounds[0].playerRounds[1].point", is(1))
                );
    }

    @Test
    void whenGetByIdNotAuthorized_thenStatusForbidden() throws Exception {
        //given
        Game game = new Game();
        //when
        Mockito.when(gameService.getById(1L)).thenReturn(game);
        //then
        mvc.perform(get("/games/1"))
                .andExpect(status().isForbidden()
                );
    }

    @Test
    void whenListByAdmin_thenReturnGameDetailResponseJson() throws Exception {
        //given
        LocalDateTime firstGameDate = LocalDateTime.now();
        Game firstGame = super.createGame(firstGameDate, true);;

        LocalDateTime secondGameDate = LocalDateTime.now();
        Game secondGame = super.createGame(secondGameDate, false);
        //when
        Mockito.when(gameService.getAll()).thenReturn(List.of(firstGame, secondGame));
        //then
        mvc.perform(get("/games")
                .header("Authorization", super.getAdminAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date", is(formatter.format(firstGameDate))))
                .andExpect(jsonPath("$[1].date", is(formatter.format(firstGameDate)))
                );
    }

    @Test
    void whenListByUser_thenStatusForbidden() throws Exception {
        //given
        Game game = new Game();
        //when
        Mockito.when(gameService.getAll()).thenReturn(List.of(game));
        //then
        mvc.perform(get("/games")
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isForbidden()
                );
    }

    @Test
    void whenListNotAuthorized_thenStatusForbidden() throws Exception {
        //given
        Game game = new Game();
        //when
        Mockito.when(gameService.getAll()).thenReturn(List.of(game));
        //then
        mvc.perform(get("/games"))
                .andExpect(status().isForbidden()
                );
    }

    @Test
    void whenListFinishedValid_thenReturnGameDetailResponseJson() throws Exception {
        //given
        LocalDateTime firstGameDate = LocalDateTime.now();
        Game firstGame = super.createGame(firstGameDate, true);;

        LocalDateTime secondGameDate = LocalDateTime.now();
        Game secondGame = super.createGame(secondGameDate, true);;
        //when
        Mockito.when(gameService.getAllFinished()).thenReturn(List.of(firstGame, secondGame));
        //then
        mvc.perform(get("/games/finished")
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date", is(formatter.format(firstGameDate))))
                .andExpect(jsonPath("$[1].date", is(formatter.format(firstGameDate)))
                );
    }

    @Test
    void whenListFinishedByUser_thenStatusForbidden() throws Exception {
        //given
        Game game = new Game();
        //when
        Mockito.when(gameService.getAll()).thenReturn(List.of(game));
        //then
        mvc.perform(get("/games/finished")
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isOk()
                );
    }

    @Test
    void whenFinishValid_thenStatusOk() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        Game game = super.createGame(now, false);
        //when
        Mockito.when(gameService.finishGame(1L)).thenReturn(game);
        //then
        mvc.perform(put("/games/1/finish")
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is(formatter.format(now)))
                );
    }

    @Test
    void whenFinishValid_thenStatusForbidden() throws Exception {
        //given
        //when
        //then
        mvc.perform(get("/games/1/finish"))
                .andExpect(status().isForbidden()
                );
    }


    @Test
    void whenDeleteValid_thenReturnPlayerResponseJson() throws Exception {
        //given
        //when
        //then
        mvc.perform(delete("/games/1")
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isOk()
                );
    }

    @Test
    void whenDeleteNotAuthorized_thenStatusForbidden() throws Exception {
        //given
        //when
        //then
        mvc.perform(delete("/games/1"))
                .andExpect(status().isForbidden()
                );
    }

    @Test
    void whenRoundValid_thenReturnRoundResponseJson() throws Exception {
        //given
        RoundAddRequest roundAddRequest = new RoundAddRequest(
                List.of(
                        super.createPlayerRound(
                                createPlayer("firstPlayer"),
                                BigDecimal.TEN
                        ),
                        super.createPlayerRound(
                                createPlayer("secondPlayer"),
                                BigDecimal.ONE
                        )
                )
        );
        //when
        Mockito.when(gameService.addRound(Mockito.anyLong(), Mockito.any(Round.class))).thenAnswer(a -> a.getArgument(1));
        //then
        mvc.perform(put("/games/1/addRound")
                .header("Authorization", super.getUserAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.toJson(roundAddRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerRounds", hasSize(2)))
                .andExpect(jsonPath("$.playerRounds[0].point", is(10)))
                .andExpect(jsonPath("$.playerRounds[1].point", is(1))
                );
    }

    @Test
    void whenRoundAddNotAuthenticated_thenStatusForbidden() throws Exception {
        //given
        //when
        //then
        mvc.perform(put("/games/1/addRound"))
                .andExpect(status().isForbidden()
                );
    }


}