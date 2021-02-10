package com.rolnik.shop.config;


import com.rolnik.shop.model.Role;
import com.rolnik.shop.model.User;
import com.rolnik.shop.security.UserAuthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@TestConfiguration
public class SecurityTestConfig {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        final List<User> users = List.of(
                new User(
                        "user",
                        "user@email.pl",
                        bCryptPasswordEncoder.encode("password"),
                        Set.of(new Role("ROLE_USER", ""))
                ),
                new User(
                        "admin",
                        "admin@email.pl",
                        bCryptPasswordEncoder.encode("password"),
                        Set.of(
                                new Role("ROLE_USER", ""),
                                new Role("ROLE_ADMIN", "")
                        )
                )
        );

        return username -> new UserAuthDetails(
                users.stream()
                        .filter(user -> username.equals(user.getUsername()))
                        .findFirst()
                        .orElse(null)
        );
    }
}
