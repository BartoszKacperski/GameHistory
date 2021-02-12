package com.rolnik.shop;

import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.Player;
import com.rolnik.shop.model.entities.PlayerRound;
import com.rolnik.shop.model.entities.Round;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BaseTest {

    protected Game createGame(LocalDateTime gameDate,
                            boolean finished,
                            Round... rounds) {
        Game game = new Game(gameDate, new ArrayList<>(), finished);

        for (Round round : rounds) {
            game.addRound(round);
        }

        return game;
    }

    protected Player createPlayer(String nickname) {
        return new Player(nickname);
    }

    protected PlayerRound createPlayerRound(Player player, BigDecimal points) {
        return new PlayerRound(
                points,
                player,
                null
        );
    }

    protected Round createRound(PlayerRound... playerRounds) {
        Round round = new Round(null, new ArrayList<>());

        for (PlayerRound playerRound : playerRounds) {
            round.addPlayerRound(playerRound);
        }

        return round;
    }
}
