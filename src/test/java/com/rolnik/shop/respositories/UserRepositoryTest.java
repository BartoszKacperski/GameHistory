package com.rolnik.shop.respositories;

import com.rolnik.shop.BaseTest;
import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest extends BaseTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindByUsername_ThenReturnUser() {
        //given
        User user = super.createBasicUser(
                "login",
                "email@email.pl",
                "password"
        );

        entityManager.persist(user);
        entityManager.flush();

        //when
        User foundUser = userRepository.findByUsername("login").orElse(null);

        //then
        assertNotNull(foundUser);
        assertEquals(foundUser.getUsername(), user.getUsername());
    }

    @Test
    void whenFindByGame_ThenReturnUserWithGame() {
        User user = super.createBasicUser(
                "login",
                "email@email.pl",
                "password"
        );
        Game game = super.createGame(
                LocalDateTime.now(),
                false
        );

        user.setCurrentGame(game);

        entityManager.persist(user);
        entityManager.persist(game);
        entityManager.flush();

        User foundUser = userRepository.findByCurrentGame(game).orElse(null);

        assertNotNull(foundUser);
        assertEquals(foundUser.getUsername(), user.getUsername());
    }

    @Test
    void whenFindByGame_ThenReturnNull() {
        User user = super.createBasicUser(
                "login",
                "email@email.pl",
                "password"
        );
        Game game = super.createGame(
                LocalDateTime.now(),
                false
        );

        entityManager.persist(user);
        entityManager.persist(game);
        entityManager.flush();

        User foundUser = userRepository.findByCurrentGame(game).orElse(null);

        assertNull(foundUser);
    }
}