package com.rolnik.shop;

import com.rolnik.shop.model.entities.Player;
import com.rolnik.shop.model.entities.User;
import com.rolnik.shop.services.PlayerRoundService;
import com.rolnik.shop.services.PlayerService;
import com.rolnik.shop.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ServiceValidationTest extends BaseTest{
    @Autowired
    private UserService userService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRoundService playerRoundService;

    @Test
    public void whenUserUsernameInvalidBelowMinSizeOnRegister_ThrowConstrainViolationException() {
        User user = super.createBasicUser(
                "u",
                "email@email.pl",
                "password"
        );

        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user));
        ConstraintViolation<?> constrainViolation = constraintViolationException.getConstraintViolations().iterator().next();
        assertNotNull(constrainViolation);
        assertEquals("registerUser.user.username", constrainViolation.getPropertyPath().toString());
    }

    @Test
    public void whenUserUsernameInvalidAboveMaxSizeOnRegister_ThrowConstrainViolationException() {
        User user = super.createBasicUser(
                StringUtils.repeat("1", 300),
                "email@email.pl",
                "password"
        );

        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user));
        ConstraintViolation<?> constrainViolation = constraintViolationException.getConstraintViolations().iterator().next();
        assertNotNull(constrainViolation);
        assertEquals("registerUser.user.username", constrainViolation.getPropertyPath().toString());
    }

    @Test
    public void whenUserEmailInvalidOnRegister_ThrowConstrainViolationException() {
        User user = super.createBasicUser(
                "username",
                "em",
                "password"
        );

        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user));
        ConstraintViolation<?> constrainViolation = constraintViolationException.getConstraintViolations().iterator().next();
        assertNotNull(constrainViolation);
        assertEquals("registerUser.user.email", constrainViolation.getPropertyPath().toString());
    }

    @Test
    public void whenUserPasswordInvalidBelowMinSizeOnRegister_ThrowConstrainViolationException() {
        User user = super.createBasicUser(
                "username",
                "email@email.pl",
                "1"
        );

        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user));
        ConstraintViolation<?> constrainViolation = constraintViolationException.getConstraintViolations().iterator().next();
        assertNotNull(constrainViolation);
        assertEquals("registerUser.user.password", constrainViolation.getPropertyPath().toString());
    }

    @Test
    public void whenUserPasswordInvalidAboveMaxSizeOnRegister_ThrowConstrainViolationException() {
        User user = super.createBasicUser(
                "username",
                "email@email.pl",
                StringUtils.repeat("1", 300)
        );

        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user));
        ConstraintViolation<?> constrainViolation = constraintViolationException.getConstraintViolations().iterator().next();
        assertNotNull(constrainViolation);
        assertEquals("registerUser.user.password", constrainViolation.getPropertyPath().toString());
    }

    @Test
    public void whenUserNullValues_ThrowConstrainViolationException() {
        User user = super.createBasicUser(
                null,
                null,
                null
        );

        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user));
        Set<ConstraintViolation<?>> constrainViolations = constraintViolationException.getConstraintViolations();
        List<String> constrainViolationsPropertyPaths = constrainViolations.stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(Path::toString)
                .collect(Collectors.toList());
        assertEquals(3, constrainViolations.size());
        assertThat(constrainViolationsPropertyPaths, hasItem("registerUser.user.password"));
        assertThat(constrainViolationsPropertyPaths, hasItem("registerUser.user.username"));
        assertThat(constrainViolationsPropertyPaths, hasItem("registerUser.user.email"));

    }

    @Test
    public void whenPlayerNicknameInvalidBelowMinSizeOnCreate_ThrowConstrainViolationException() {
        Player player = super.createPlayer(
                "12"
        );

        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> playerService.create(player));
        ConstraintViolation<?> constrainViolation = constraintViolationException.getConstraintViolations().iterator().next();
        assertNotNull(constrainViolation);
        assertEquals("create.player.nickname", constrainViolation.getPropertyPath().toString());
    }

    @Test
    public void whenPlayerNicknameInvalidAboveMaxSizeOnCreate_ThrowConstrainViolationException() {
        Player player = super.createPlayer(
                StringUtils.repeat("1", 300)
        );

        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> playerService.create(player));
        ConstraintViolation<?> constrainViolation = constraintViolationException.getConstraintViolations().iterator().next();
        assertNotNull(constrainViolation);
        assertEquals("create.player.nickname", constrainViolation.getPropertyPath().toString());
    }

    @Test
    public void whenPointNotValidFractionAboveMax_ThrowConstrainViolationException() {
        assertThrows(ConstraintViolationException.class, () -> playerRoundService.updatePoint(1L, new BigDecimal(2.3234)));
    }

    @Test
    public void whenPointNotValidIntegerAboveMax_ThrowConstrainViolationException() {
        assertThrows(ConstraintViolationException.class, () -> playerRoundService.updatePoint(1L, new BigDecimal(StringUtils.repeat("1", 20))));
    }
}
