package com.rolnik.shop.services;

import com.rolnik.shop.exceptions.EntityNotFoundException;
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

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class));
    }

    public Game getCurrentGame(Long userId) {
        return this.getById(userId).getCurrentGame();
    }

    public void resetUserCurrentGame(Game game) {
        Optional<User> user = userRepository.findByCurrentGame(game);

        user.ifPresent(User::resetCurrentGame);
    }

    public User registerUser(@Valid User user) {
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

    public Long getCurrentUserId(Authentication authentication) {
        Object user = authentication.getPrincipal();

        if (user instanceof UserAuthDetails) {
            return ((UserAuthDetails)user).getId();
        }

        return 1L;
    }
}
