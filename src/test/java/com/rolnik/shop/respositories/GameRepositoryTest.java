package com.rolnik.shop.respositories;

import com.rolnik.shop.BaseTest;
import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.User;
import net.bytebuddy.asm.Advice;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class GameRepositoryTest extends BaseTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void whenListByFinished_thenReturnGameList() {
        //given
        LocalDateTime firstDate = LocalDateTime.now();
        Game firstGame = super.createGame(
                firstDate,
                true
        );
        LocalDateTime secondDate = LocalDateTime.now();
        Game secondGame = super.createGame(
                firstDate,
                true
        );
        LocalDateTime thirdDate = LocalDateTime.now();
        Game thirdGame = super.createGame(
                firstDate,
                true
        );
        LocalDateTime fourthDate = LocalDateTime.now();
        Game fourthGame = super.createGame(
                firstDate,
                false
        );

        entityManager.persist(firstGame);
        entityManager.persist(secondGame);
        entityManager.persist(thirdGame);
        entityManager.persist(fourthGame);

        //when
        List<Game> finishedGames = gameRepository.getAllByFinished(true);
        List<Game> notFinishedGames = gameRepository.getAllByFinished(false);

        //then
        Assert.assertEquals(3, finishedGames.size());
        Assert.assertEquals(1, notFinishedGames.size());
        Assert.assertEquals(firstDate, finishedGames.get(0).getDate());
        Assert.assertEquals(secondDate, finishedGames.get(1).getDate());
        Assert.assertEquals(thirdDate, finishedGames.get(2).getDate());
        Assert.assertEquals(fourthDate, notFinishedGames.get(0).getDate());
    }

    @Test
    public void whenListByUser_thenReturnGameList() {
        //given
        LocalDateTime firstDate = LocalDateTime.now();
        User user = entityManager.persist(super.createBasicUser());
        User admin = entityManager.persist(super.createBasicUser(
                "admin",
                "admin@email.pl",
                "password"
        ));

        Game firstGame = super.createGame(
                firstDate
        );
        LocalDateTime secondDate = LocalDateTime.now();
        Game secondGame = super.createGame(
                firstDate
        );
        LocalDateTime thirdDate = LocalDateTime.now();
        Game thirdGame = super.createGame(
                firstDate
        );
        LocalDateTime fourthDate = LocalDateTime.now();
        Game fourthGame = super.createGame(
                firstDate
        );

        entityManager.persist(firstGame);
        entityManager.persist(secondGame);
        entityManager.persist(thirdGame);
        entityManager.persist(fourthGame);

        //when

        //then
    }
}