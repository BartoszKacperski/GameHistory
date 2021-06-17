package com.rolnik.shop.services;

import com.rolnik.shop.exceptions.EntityNotFoundException;
import com.rolnik.shop.exceptions.UserCredentialsAlreadyInUseException;
import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.Role;
import com.rolnik.shop.model.entities.User;
import com.rolnik.shop.respositories.RoleRepository;
import com.rolnik.shop.respositories.UserRepository;
import com.rolnik.shop.security.UserAuthDetails;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor

@Service
@Validated
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Game getCurrentGame(User user) {
        return user.getCurrentGame();
    }

    public User registerUser(@Valid User user) {
        checkUserCredentialsInUse(user);
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        Role userRole = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException(Role.class));

        user.setRoles(Set.of(userRole));

        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("userNotFound"));

        return new UserAuthDetails(user);
    }

    private void checkUserCredentialsInUse(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserCredentialsAlreadyInUseException("username");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserCredentialsAlreadyInUseException("email");
        }
    }
}
