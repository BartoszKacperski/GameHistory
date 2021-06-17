package com.rolnik.shop.entitites;

import com.rolnik.shop.BaseTest;
import com.rolnik.shop.model.entities.*;
import com.rolnik.shop.model.entities.base.SimpleEntityWithDates;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@DataJpaTest
class EntitiesTest extends BaseTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenCreateUser_ThenCreationsDateIsValid() {
        testDates(super.createBasicUser(), User::getCreationDate);
    }

    @Test
    public void whenCreateRound_ThenCreationsDateIsValid() {
        testDates(super.createRound(), Round::getCreationDate);
    }

    @Test
    public void whenCreatePlayer_ThenCreationsDateIsValid() {
        testDates(super.createPlayer("test"), Player::getCreationDate);
    }

    @Test
    public void whenCreateGame_ThenCreationsDateIsValid() {
        testDates(super.createGame(LocalDateTime.now()), Game::getCreationDate);
    }

    @Test
    public void whenCreatePlayerRound_ThenCreationsDateIsValid() {
        testDates(super.createPlayerRound(
                entityManager.merge(super.createPlayer("test")),
                BigDecimal.ZERO
        ), PlayerRound::getCreationDate);
    }

    @Test
    public void whenUpdateUser_ThenUpdateDateIsValid() {
        User user = super.createBasicUser();

        user = entityManager.merge(user);
        entityManager.flush();
        entityManager.clear();

        user.setUsername("test");
        testDates(user, User::getLastUpdateDate);
    }

    @Test
    public void whenUpdateRound_ThenUpdateDateIsValid() {
        Round round = super.createRound();

        round = entityManager.merge(round);
        entityManager.flush();
        entityManager.clear();

        round.setGame(
                entityManager.merge(
                        super.createGame(LocalDateTime.now())
                )
        );
        testDates(round, Round::getLastUpdateDate);
    }

    @Test
    public void whenUpdatePlayer_ThenUpdateDateIsValid() {
        Player player = super.createPlayer("test");

        player = entityManager.merge(player);
        entityManager.flush();
        entityManager.clear();

        player.setNickname("test123");
        testDates(player, Player::getLastUpdateDate);
    }

    @Test
    public void whenUpdateGame_ThenUpdatesDateIsValid() {
        Game game = super.createGame(LocalDateTime.now());

        game = entityManager.merge(game);
        entityManager.flush();
        entityManager.clear();

        game.setFinished(true);
        testDates(game, Game::getLastUpdateDate);
    }


    @Test
    public void whenUpdatePlayerRound_ThenUpdatesDateIsValid() {
        PlayerRound playerRound = super.createPlayerRound(
                entityManager.merge(super.createPlayer("test")),
                BigDecimal.ONE
        );

        playerRound = entityManager.merge(playerRound);
        entityManager.flush();
        entityManager.clear();

        playerRound.setPoint(BigDecimal.TEN);
        testDates(playerRound, PlayerRound::getLastUpdateDate);
    }

    private <T extends SimpleEntityWithDates> void testDates(T entity, Function<T, LocalDateTime> dateExtractor) {
        LocalDateTime beforeMerge = LocalDateTime.now();
        entity = entityManager.merge(entity);
        entityManager.flush();
        entityManager.clear();
        LocalDateTime afterMerge = LocalDateTime.now();

        LocalDateTime entityDate = dateExtractor.apply(entity);
        Assert.assertTrue(
                dateAssertMessage(dateExtractor.apply(entity), beforeMerge, "after"),
                entityDate.isAfter(beforeMerge) || entityDate.equals(beforeMerge)
        );
        Assert.assertTrue(
                dateAssertMessage(dateExtractor.apply(entity), beforeMerge, "before"),
                entityDate.isBefore(afterMerge) || entityDate.equals(afterMerge)
        );
    }

    private String dateAssertMessage(LocalDateTime expected, LocalDateTime actual, String compare) {
        return String.format(
                "Expected date %s to be %s %s",
                expected,
                compare,
                actual
        );
    }

}
