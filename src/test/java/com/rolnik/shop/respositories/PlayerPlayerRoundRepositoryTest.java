package com.rolnik.shop.respositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
class PlayerPlayerRoundRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlayerRoundRepository playerRoundRepository;

    @Test
    void whenRoundsExists_ThenReturnPointGroupedByPlayer() {

    }
}