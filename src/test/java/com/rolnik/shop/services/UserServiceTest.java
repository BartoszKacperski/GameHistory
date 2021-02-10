package com.rolnik.shop.services;

import com.rolnik.shop.model.Role;
import com.rolnik.shop.model.User;
import com.rolnik.shop.respositories.RoleRepository;
import com.rolnik.shop.respositories.UserRepository;
import com.rolnik.shop.security.UserAuthDetails;
import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
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
        User user = new User(
                "username",
                "test@test.pl",
                "test",
                Sets.newHashSet()
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
    public void whenRoleUserNotFound_thenThrowException() {
        //given
        User user = new User(
                "username",
                "test@test.pl",
                "test",
                Sets.newHashSet()
        );
        //when
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());
        //then
        Assert.assertThrows(RuntimeException.class, () -> userService.registerUser(user));
    }

    @Test
    public void whenUsernameValid_thenReturnUser() {
        User user = new User(
                "username",
                "test@test.pl",
                "test",
                Sets.newHashSet()
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
        User user = new User(
                "username",
                "test@test.pl",
                "test",
                Sets.newHashSet()
        );
        //when
        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        //then
        Assert.assertThrows(RuntimeException.class, () -> userService.loadUserByUsername("username"));
    }
}