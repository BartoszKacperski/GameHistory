package com.rolnik.shop.services;

import com.rolnik.shop.model.Role;
import com.rolnik.shop.model.User;
import com.rolnik.shop.respositories.RoleRepository;
import com.rolnik.shop.respositories.UserRepository;
import com.rolnik.shop.security.UserAuthDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(User user) {
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        Role userRole = roleRepository
                .findByName("ROLE_USER")
                //TODO Change exception
                .orElseThrow(() -> new RuntimeException("roleDoesNotExists"));

        user.setRoles(Set.of(userRole));

        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("userNotFound"));

        return new UserAuthDetails(user);
    }
}
