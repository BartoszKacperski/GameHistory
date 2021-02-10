package com.rolnik.shop.services;

import com.rolnik.shop.model.Player;
import com.rolnik.shop.respositories.PlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void whenPlayerValid_thenCreateAndReturnPlayer() {
        //when
        Player player = new Player("nickname");
        //given
        Mockito.when(playerRepository.save(player)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        //then
        Player createdPlayer = playerService.create(player);

        Assert.assertEquals("nickname", createdPlayer.getNickname());
    }

    @Test
    void whenIdValid_thenReturnPlayer() {
        //when
        Player player = new Player("nickname");
        //given
        Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        //then
        Player foundPlayer = playerService.getById(1);

        Assert.assertNotNull(foundPlayer);
        Assert.assertEquals("nickname", foundPlayer.getNickname());
    }

    @Test
    void whenIdNotValid_thenThrowException() {
        //when
        Player player = new Player("nickname");
        //given
        Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        Assert.assertThrows(RuntimeException.class, () -> playerService.getById(1L));
    }

    @Test
    void whenPlayersExist_thenReturnAll() {
        //when
        Player firstPlayer = new Player("first");
        Player secondPlayer = new Player("second");
        Player thirdPlayer = new Player("third");
        //given
        Mockito.when(playerRepository.findAll()).thenReturn(List.of(firstPlayer, secondPlayer, thirdPlayer));
        //then
        List<Player> foundPlayers = playerService.getAll();

        Assert.assertNotNull(foundPlayers);
        Assert.assertEquals(3, foundPlayers.size());
        Assert.assertEquals("first", foundPlayers.get(0).getNickname());
        Assert.assertEquals("second", foundPlayers.get(1).getNickname());
        Assert.assertEquals("third", foundPlayers.get(2).getNickname());
    }

    @Test
    void whenPlayerValidUpdate_thenUpdateAndReturnUser() {
        //when
        Player player = new Player("nickname");
        //given
        Mockito.when(playerRepository.save(player)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        //then
        Player updatedPlayer = playerService.update(player);

        Assert.assertEquals("nickname", updatedPlayer.getNickname());
    }
}