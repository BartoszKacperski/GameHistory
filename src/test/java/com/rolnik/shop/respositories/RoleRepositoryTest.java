package com.rolnik.shop.respositories;

import com.rolnik.shop.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void whenFindByName_ThenReturnRole() {
        //given
        Role role = new Role(
                "TEST_ROLE",
                "TEST DESCRIPTION"
        );

        entityManager.persist(role);
        entityManager.flush();

        //when
        Role foundRole = roleRepository.findByName("TEST_ROLE").orElse(null);

        //then
        assertNotNull(foundRole);
        assertEquals(foundRole.getName(), role.getName());

    }
}