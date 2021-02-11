package com.rolnik.shop.services;

import com.rolnik.shop.exceptions.EntityNotFoundException;
import com.rolnik.shop.model.entities.Player;
import com.rolnik.shop.model.entities.PlayerRound;
import com.rolnik.shop.respositories.PlayerRoundRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerRoundServiceTest {
    @Mock
    private PlayerRoundRepository playerRoundRepository;

    @InjectMocks
    private PlayerRoundService playerRoundService;

    @Test
    void whenIdValid_thenReturnPlayerRound() {
        //when
        Player player = new Player("nickname");
        PlayerRound playerRound = new PlayerRound(
                BigDecimal.TEN,
                player,
                null
        );
        //given
        Mockito.when(playerRoundRepository.findById(1L)).thenReturn(Optional.of(playerRound));
        //then
        PlayerRound foundPlayerRound = playerRoundService.getById(1L);

        Assert.assertNotNull(foundPlayerRound);
        Assert.assertEquals("nickname", foundPlayerRound.getPlayer().getNickname());
        Assert.assertEquals(BigDecimal.TEN, foundPlayerRound.getPoint());
    }

    @Test
    void whenIdNotValid_thenThrowException() {
        //when
        PlayerRound playerRound = new PlayerRound(
                BigDecimal.TEN,
                null,
                null
        );
        //given
        Mockito.when(playerRoundRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        Assert.assertThrows(EntityNotFoundException.class, () -> playerRoundService.getById(1L));
    }

    @Test
    void whenPointUpdateValid_thenReturnPlayerRound() {
        //when
        Player player = new Player("nickname");
        PlayerRound playerRound = new PlayerRound(
                BigDecimal.TEN,
                player,
                null
        );
        //given
        Mockito.when(playerRoundRepository.findById(1L)).thenReturn(Optional.of(playerRound));
        //then
        PlayerRound updatedPlayerRound = playerRoundService.updatePoint(1L, BigDecimal.valueOf(100));

        Assert.assertNotNull(updatedPlayerRound);
        Assert.assertEquals("nickname", updatedPlayerRound.getPlayer().getNickname());
        Assert.assertEquals(BigDecimal.valueOf(100), updatedPlayerRound.getPoint());
    }

    @Test
    void whenPointUpdatedNotValid_thenThrowException() {
        //when
        //given
        Mockito.when(playerRoundRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        Assert.assertThrows(RuntimeException.class, () -> playerRoundService.updatePoint(1L, BigDecimal.valueOf(100)));
    }
}