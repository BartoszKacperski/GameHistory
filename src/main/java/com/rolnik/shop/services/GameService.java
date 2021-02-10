package com.rolnik.shop.services;

import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.PlayerRound;
import com.rolnik.shop.model.entities.Round;
import com.rolnik.shop.respositories.GameRepository;
import com.rolnik.shop.respositories.RoundRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final RoundRepository roundRepository;
    private final EntityManager entityManager;

    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public Game getById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("game not found"));
    }

    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    @Transactional
    public Round addRound(Long gameId, Round round) {
        Game game = this.getById(gameId);

        for (PlayerRound playerRound : round.getPlayerRounds()) {
            playerRound.setRound(round);
        }
        game.addRound(round);

        return roundRepository.saveAndFlush(round);
    }
}
