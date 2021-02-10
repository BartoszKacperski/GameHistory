package com.rolnik.shop.services;

import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.Player;
import com.rolnik.shop.model.entities.PlayerRound;
import com.rolnik.shop.model.entities.Round;
import com.rolnik.shop.respositories.GameRepository;
import com.rolnik.shop.respositories.RoundRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private RoundRepository roundRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    void whenGameValid_thenCreateAndReturnGame() {
        //when
        LocalDateTime now = LocalDateTime.now();
        Game game = new Game(now, List.of());
        //given
        Mockito.when(gameRepository.save(game)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        //then
        Game createdGame = gameService.create(game);

        Assert.assertEquals(now, createdGame.getDate());
        Assert.assertEquals(0, createdGame.getRounds().size());
    }

    @Test
    void whenIdValid_thenReturnGame() {
        //when
        LocalDateTime now = LocalDateTime.now();
        Game game = new Game(now, List.of());
        //given
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        //then
        Game foundGame = gameService.getById(1L);

        Assert.assertNotNull(foundGame);
        Assert.assertEquals(now, foundGame.getDate());
        Assert.assertEquals(0, foundGame.getRounds().size());
    }

    @Test
    void whenIdNotValid_thenThrowException() {
        //when
        LocalDateTime now = LocalDateTime.now();
        Game game = new Game(now, List.of());
        //given
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        Assert.assertThrows(RuntimeException.class, () -> gameService.getById(1L));
    }

    @Test
    void whenPlayersExist_thenReturnAll() {
        //when
        LocalDateTime now1 = LocalDateTime.now();
        Game firstGame = new Game(now1, List.of());
        LocalDateTime now2 = LocalDateTime.now();
        Game secondGame = new Game(now2, List.of());
        LocalDateTime now3 = LocalDateTime.now();
        Game thirdGame = new Game(now3, List.of());
        //given
        Mockito.when(gameRepository.findAll()).thenReturn(List.of(firstGame, secondGame, thirdGame));
        //then
        List<Game> foundGames = gameService.getAll();

        Assert.assertNotNull(foundGames);
        Assert.assertEquals(3, foundGames.size());
        Assert.assertEquals(now1, foundGames.get(0).getDate());
        Assert.assertEquals(now2, foundGames.get(1).getDate());
        Assert.assertEquals(now3, foundGames.get(2).getDate());
    }

    @Test
    void whenRoundValid_thenAddRoundToGame() {
        //when
        Player firstPlayer = new Player("firstPlayer");
        Player secondPlayer = new Player("secondPlayer");
        Game game = new Game(LocalDateTime.now(), new ArrayList<>());
        Round round = new Round(
                game,
                List.of(
                        new PlayerRound(
                            BigDecimal.TEN,
                            firstPlayer,
                            null
                        ),
                        new PlayerRound(
                                BigDecimal.ONE,
                                secondPlayer,
                                null
                        )
                )
        );
        //given
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        Mockito.when(roundRepository.saveAndFlush(Mockito.any(Round.class))).thenAnswer(a -> a.getArgument(0));
        //then
        Round createdRound = gameService.addRound(1L, round);

        Assert.assertNotNull(createdRound);
        Assert.assertEquals(2, createdRound.getPlayerRounds().size());

        PlayerRound first = createdRound.getPlayerRounds().get(0);
        PlayerRound second = createdRound.getPlayerRounds().get(1);

        Assert.assertEquals("firstPlayer", first.getPlayer().getNickname());
        Assert.assertEquals("secondPlayer", second.getPlayer().getNickname());
        Assert.assertEquals(BigDecimal.TEN, first.getPoint());
        Assert.assertEquals(BigDecimal.ONE, second.getPoint());
    }
}