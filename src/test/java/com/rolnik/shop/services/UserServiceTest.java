package com.rolnik.shop.services;

import com.rolnik.shop.BaseTest;
import com.rolnik.shop.exceptions.EntityNotFoundException;
import com.rolnik.shop.exceptions.UserCredentialsAlreadyInUseException;
import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.Role;
import com.rolnik.shop.model.entities.User;
import com.rolnik.shop.respositories.RoleRepository;
import com.rolnik.shop.respositories.UserRepository;
import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest extends BaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    public void whenUserRegister_thenOnlyRoleUserAndEncodedPassword() {
        //given
        User user = super.createBasicUser(
                "username",
                "test@test.pl",
                "test"
        );
        //when
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(new Role("ROLE_USER", "DESCRIPTION")));
        Mockito.when(bCryptPasswordEncoder.encode("test")).thenReturn("test_encoded");
        Mockito.when(userRepository.save(user)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        //then
        User registeredUser = userService.registerUser(user);

        assertNotNull(user);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
        assertEquals(1, registeredUser.getRoles().size());
        assertEquals("ROLE_USER", registeredUser.getRoles().iterator().next().getName());
        assertEquals("test_encoded", registeredUser.getPassword());
    }

    @Test
    public void whenUserRegisterDuplicatedUsername_thenThrowUserCredentialsAlreadyInUseException() {
        //given
        User user = super.createBasicUser(
                "username",
                "test@test.pl",
                "test"
        );
        //when
        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        //then
        Throwable exception = Assert.assertThrows(UserCredentialsAlreadyInUseException.class, () -> userService.registerUser(user));
        Assert.assertEquals("username", exception.getMessage());
    }

    @Test
    public void whenUserRegisterDuplicatedEmail_thenThrowUserCredentialsAlreadyInUseException() {
        //given
        User user = super.createBasicUser(
                "username",
                "test@test.pl",
                "test"
        );
        //when
        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByEmail("test@test.pl")).thenReturn(Optional.of(user));
        //then
        Throwable exception = Assert.assertThrows(UserCredentialsAlreadyInUseException.class, () -> userService.registerUser(user));
        Assert.assertEquals("email", exception.getMessage());
    }

    @Test
    public void whenRoleUserNotFound_thenThrowException() {
        //given
        User user = super.createBasicUser(
                "username",
                "test@test.pl",
                "test"
        );
        //when
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());
        //then
        Assert.assertThrows(EntityNotFoundException.class, () -> userService.registerUser(user));
    }

    @Test
    public void whenUsernameValid_thenReturnUser() {
        User user = super.createBasicUser(
                "username",
                "test@test.pl",
                "test"
        );
        //when
        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        //then
        UserDetails foundUser = userService.loadUserByUsername("username");

        Assert.assertEquals("username", foundUser.getUsername());
        Assert.assertEquals("test", foundUser.getPassword());
    }

    @Test
    public void whenUsernameNotValid_thenThrowException() {
        //given
        User user = super.createBasicUser(
                "username",
                "test@test.pl",
                "test"
        );
        //when
        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        //then
        Assert.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("username"));
    }

    @Test
    public void whenGetCurrentGame_thenReturnUserCurrentGame() {
        //given
        User user = super.createBasicUser(
                "username",
                "test@test.pl",
                "test"
        );
        LocalDateTime now = LocalDateTime.now();
        Game game = super.createGame(
                now,
                false
        );

        user.setCurrentGame(game);
        //when
        //then
        Game foundGame = userService.getCurrentGame(user);

        Assert.assertEquals(now, foundGame.getDate());
        Assert.assertEquals(game, user.getCurrentGame());
        Assert.assertFalse(game.isFinished());
    }

    @Test
    public void whenGetCurrentGame_thenReturnNull() {
        //given
        User user = super.createBasicUser(
                "username",
                "test@test.pl",
                "test"
        );
        //when
        //then
        Game foundGame = userService.getCurrentGame(user);

        Assert.assertNull(foundGame);
    }
}