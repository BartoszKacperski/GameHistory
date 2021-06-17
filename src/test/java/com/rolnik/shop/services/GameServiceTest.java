package com.rolnik.shop.services;

import com.rolnik.shop.BaseTest;
import com.rolnik.shop.exceptions.CurrentGameAlreadyExistsException;
import com.rolnik.shop.exceptions.EntityNotFoundException;
import com.rolnik.shop.exceptions.FinishedGameUpdateException;
import com.rolnik.shop.exceptions.FinishingNotOwnCurrentGameException;
import com.rolnik.shop.model.entities.*;
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
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GameServiceTest extends BaseTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private RoundRepository roundRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private GameService gameService;

    @Test
    void whenGameValid_thenCreateAndReturnGame() {
        //when
        LocalDateTime now = LocalDateTime.now();
        Game game = super.createGame(now, false);
        User user = super.createBasicUser("username", "email@email.pl", "password");
        //given
        Mockito.when(gameRepository.save(game)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        //then
        Game createdGame = gameService.create(game, user);

        Assert.assertEquals(now, createdGame.getDate());
        Assert.assertEquals(0, createdGame.getRounds().size());
        Assert.assertEquals(game, user.getCurrentGame());
    }

    @Test
    public void whenUserHasCurrentGame_ThenThrowCurrentGameAlreadyExistsException() {
        //when
        User user = super.createBasicUser();
        Game firstGame = super.createGame(LocalDateTime.now(), true);
        Game secondGame = super.createGame(LocalDateTime.now(), true);

        user.setCurrentGame(firstGame);
        //given
        Mockito.when(gameRepository.save(secondGame)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        //then
        Assert.assertThrows(CurrentGameAlreadyExistsException.class, () -> gameService.create(secondGame, user));
    }

    @Test
    void whenIdValid_thenReturnGame() {
        //when
        LocalDateTime now = LocalDateTime.now();
        Game game = super.createGame(now, false);
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
        Game game = super.createGame(now, false);
        //given
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        Assert.assertThrows(EntityNotFoundException.class, () -> gameService.getById(1L));
    }

    @Test
    void whenPlayersExist_thenReturnAll() {
        //when
        LocalDateTime now1 = LocalDateTime.now();
        Game firstGame = super.createGame(now1, false);
        LocalDateTime now2 = LocalDateTime.now();
        Game secondGame = super.createGame(now2, false);;
        LocalDateTime now3 = LocalDateTime.now();
        Game thirdGame = super.createGame(now3, false);
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
        Player firstPlayer = super.createPlayer("firstPlayer");
        Player secondPlayer = super.createPlayer("secondPlayer");
        Round round = super.createRound(
                super.createPlayerRound(firstPlayer, BigDecimal.TEN),
                super.createPlayerRound(secondPlayer, BigDecimal.ONE)
        );
        Game game = super.createGame(LocalDateTime.now(), false, round);

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

    @Test
    void whenRoundValidButGameFinished_thenThenThrowFinishedGameUpdateException() {
        //when
        Player firstPlayer = super.createPlayer("firstPlayer");
        Player secondPlayer = super.createPlayer("secondPlayer");
        Round round = super.createRound(
                super.createPlayerRound(firstPlayer, BigDecimal.TEN),
                super.createPlayerRound(secondPlayer, BigDecimal.ONE)
        );
        Game game = super.createGame(LocalDateTime.now(), true, round);
        //given
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        //then
        Assert.assertThrows(FinishedGameUpdateException.class, () -> gameService.addRound(1L, round));
    }

    @Test
    public void whenGameFinish_ThenGameFinished() {
        //when
        User user = super.createBasicUser();
        Game game = super.createGame(LocalDateTime.now(), false);

        user.setCurrentGame(game);
        //given
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        //then
        Game updatedGame = gameService.finishGame(1L, user);

        Assert.assertTrue(updatedGame.isFinished());
    }

    @Test
    public void whenGameFinish_ThenUserGameNull() {
        //when
        Game game = super.createGame(LocalDateTime.now(), false);
        User user = super.createBasicUser("username", "email@Email.pl", "password");
        user.setCurrentGame(game);
        //given
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        //then
        Game updatedGame = gameService.finishGame(1L, user);

        Assert.assertTrue(updatedGame.isFinished());
        Assert.assertNull(user.getCurrentGame());
    }

    @Test
    public void whenFinishNotOwnGame_ThenThrowFinishingNotOwnCurrentGameException() {
        //when
        User firstUser = super.createBasicUser();
        User secondUser = super.createBasicUser();
        Game game = super.createGame(LocalDateTime.now(), false);

        firstUser.setCurrentGame(game);
        //given
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        //then
        Assert.assertThrows(FinishingNotOwnCurrentGameException.class, () -> gameService.finishGame(1L, secondUser));

    }

    @Test
    public void whenGetAllFinished_ThenListContainsOnlyFinishedGames() {
        //when
        Game firstGame = super.createGame(LocalDateTime.now(), true);
        Game secondGame = super.createGame(LocalDateTime.now(), true);
        Game thirdGame = super.createGame(LocalDateTime.now(), true);

        //given
        Mockito.when(gameRepository.getAllByFinished(true)).thenReturn(List.of(firstGame, secondGame, thirdGame));
        //then
        List<Game> finishedGames = gameService.getAllFinished();

        Assert.assertEquals(3 , finishedGames.size());
        Assert.assertTrue(finishedGames.contains(firstGame));
        Assert.assertTrue(finishedGames.contains(secondGame));
        Assert.assertTrue(finishedGames.contains(thirdGame));
    }

}