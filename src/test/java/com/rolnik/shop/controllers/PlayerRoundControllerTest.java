package com.rolnik.shop.controllers;

import com.rolnik.shop.dtos.player.PlayerCreateRequest;
import com.rolnik.shop.model.entities.Player;
import com.rolnik.shop.model.entities.PlayerRound;
import com.rolnik.shop.services.PlayerRoundService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(PlayerRoundController.class)
class PlayerRoundControllerTest extends BaseControllerTest {
    @MockBean
    private PlayerRoundService playerRoundService;

    @Test
    void whenUpdatePointAuthorized_thenReturnPlayerRoundShortDetailsResponseJson() throws Exception {
        //given
        BigDecimal newPointValue = BigDecimal.valueOf(250);
        Player player = super.createPlayer("nickname");
        PlayerRound playerRound = super.createPlayerRound(
                player,
                newPointValue
        );
        //when
        Mockito.when(playerRoundService.updatePoint(1L, newPointValue)).thenReturn(playerRound);
        //then
        mvc.perform(put("/playerRounds/1")
                .header("Authorization", super.getUserAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPointValue.toString().getBytes()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.point", is(250)));
    }
    @Test

    void whenUpdatePointNotAuthorized_thenReturnStatusForbidden() throws Exception {
        //given
        BigDecimal newPointValue = BigDecimal.valueOf(250);
        //when
        //then
        mvc.perform(put("/playerRounds/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPointValue.toString().getBytes()))
                .andExpect(status().isForbidden());
    }
}