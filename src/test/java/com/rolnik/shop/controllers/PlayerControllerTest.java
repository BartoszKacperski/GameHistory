package com.rolnik.shop.controllers;

import com.rolnik.shop.dtos.player.PlayerCreateRequest;
import com.rolnik.shop.dtos.player.PlayerUpdateRequest;
import com.rolnik.shop.model.entities.Player;
import com.rolnik.shop.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Import(PlayerController.class)
class PlayerControllerTest extends BaseControllerTest {
    @MockBean
    private PlayerService playerService;

    @Test
    void whenCreateAuthorized_thenReturnPlayerResponseJson() throws Exception {
        //given
        PlayerCreateRequest playerCreateRequest = new PlayerCreateRequest(
                "nickname"
        );
        //when
        Mockito.when(playerService.create(Mockito.any())).thenAnswer(answer -> answer.getArgument(0));
        //then
        mvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.toJson(playerCreateRequest))
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nickname", is("nickname"))
                );
    }

    @Test
    void whenCreateNotAuthorized_thenStatusForbidden() throws Exception {
        //given
        PlayerCreateRequest playerCreateRequest = new PlayerCreateRequest(
                "nickname"
        );
        //when
        Mockito.when(playerService.create(Mockito.any())).thenAnswer(answer -> answer.getArgument(0));
        //then
        mvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.toJson(playerCreateRequest)))
                .andExpect(status().isForbidden()
                );
    }

    @Test
    void whenGetByIdValid_thenReturnPlayerResponseJson() throws Exception {
        //given
        Player player = new Player(
                "nickname"
        );
        //when
        Mockito.when(playerService.getById(1L)).thenReturn(player);
        //then
        mvc.perform(get("/players/1")
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname", is("nickname"))
                );
    }

    @Test
    void whenGetByIdNotAuthorized_thenStatusForbidden() throws Exception {
        //given
        Player player = new Player(
                "nickname"
        );
        //when
        Mockito.when(playerService.getById(1L)).thenReturn(player);
        //then
        mvc.perform(get("/players/1"))
                .andExpect(status().isForbidden()
                );
    }

    @Test
    void whenListValid_thenReturnPlayerResponseJson() throws Exception {
        //given
        Player firstPlayer = new Player(
                "firstPlayer"
        );
        Player secondPlayer = new Player(
                "secondPlayer"
        );
        //when
        Mockito.when(playerService.getAll()).thenReturn(List.of(firstPlayer, secondPlayer));
        //then
        mvc.perform(get("/players")
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nickname", is("firstPlayer")))
                .andExpect(jsonPath("$[1].nickname", is("secondPlayer"))
                );
    }

    @Test
    void whenListNotAuthorized_thenStatusForbidden() throws Exception {
        //given
        Player firstPlayer = new Player(
                "firstPlayer"
        );
        Player secondPlayer = new Player(
                "secondPlayer"
        );
        //when
        Mockito.when(playerService.getAll()).thenReturn(List.of(firstPlayer, secondPlayer));
        //then
        mvc.perform(get("/players"))
                .andExpect(status().isForbidden()
                );
    }

    @Test
    void whenUpdateValid_thenReturnPlayerResponseJson() throws Exception {
        //given
        PlayerUpdateRequest playerUpdateRequest = new PlayerUpdateRequest(
                1L,
                "newNickname"
        );
        //when
        Mockito.when(playerService.update(Mockito.any())).thenAnswer(answer -> answer.getArgument(0));
        //then
        mvc.perform(put("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.toJson(playerUpdateRequest))
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname", is("newNickname"))
                );
    }

    @Test
    void whenUpdateNotAuthorized_thenStatusForbidden() throws Exception {
        //given
        PlayerUpdateRequest playerUpdateRequest = new PlayerUpdateRequest(
                1L,
                "newNickname"
        );
        //when
        Mockito.when(playerService.update(Mockito.any())).thenAnswer(answer -> answer.getArgument(0));
        //then
        mvc.perform(put("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.toJson(playerUpdateRequest)))
                .andExpect(status().isForbidden()
                );
    }

    @Test
    void whenDeleteValid_thenReturnPlayerResponseJson() throws Exception {
        //given
        //when
        //then
        mvc.perform(delete("/players/1")
                .header("Authorization", super.getUserAuthToken()))
                .andExpect(status().isOk()
                );
    }

    @Test
    void whenDeleteNotAuthorized_thenStatusForbidden() throws Exception {
        //given
        //when
        //then
        mvc.perform(delete("/players/1"))
                .andExpect(status().isForbidden()
                );
    }
}