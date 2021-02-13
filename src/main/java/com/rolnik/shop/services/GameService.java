package com.rolnik.shop.services;

import com.rolnik.shop.exceptions.CurrentGameAlreadyExistsException;
import com.rolnik.shop.exceptions.EntityNotFoundException;
import com.rolnik.shop.exceptions.FinishedGameUpdateException;
import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.PlayerRound;
import com.rolnik.shop.model.entities.Round;
import com.rolnik.shop.model.entities.User;
import com.rolnik.shop.respositories.GameRepository;
import com.rolnik.shop.respositories.RoundRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor

@Service
@Validated
public class GameService {
    private final GameRepository gameRepository;
    private final RoundRepository roundRepository;
    private final UserService userService;

    @Transactional
    public Game create(@Valid Game game, Long userId) {
        User user = userService.getById(userId);

        Game createdGame = gameRepository.save(game);

        if (user.getCurrentGame() != null) {
            throw new CurrentGameAlreadyExistsException();
        }
        user.setCurrentGame(game);

        return createdGame;
    }

    public Game getById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Game.class));
    }

    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    public List<Game> getAllFinished() {
        return gameRepository.getAllByFinished(true);
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    @Transactional
    public Game finishGame(Long id) {
        Game game = this.getById(id);

        game.setFinished(true);
        userService.resetUserCurrentGame(game);

        return game;
    }

    @Transactional
    public Round addRound(Long gameId, @Valid Round round) {
        Game game = this.getById(gameId);

        if (game.isFinished()) {
            throw new FinishedGameUpdateException();
        }

        for (PlayerRound playerRound : round.getPlayerRounds()) {
            playerRound.setRound(round);
        }
        game.addRound(round);

        return roundRepository.saveAndFlush(round);
    }
}
