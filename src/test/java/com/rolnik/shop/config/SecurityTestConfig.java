package com.rolnik.shop.config;


import com.rolnik.shop.model.entities.Role;
import com.rolnik.shop.model.entities.User;
import com.rolnik.shop.security.UserAuthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
import java.util.Set;

@TestConfiguration
public class SecurityTestConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        User user = User.builder()
                .username("user")
                .email("user@email.pl")
                .password("password")
                .roles(Set.of(new Role("ROLE_USER", "")))
                .build();

        User admin = User.builder()
                .username("admin")
                .email("admin@email.pl")
                .password("password")
                .roles(Set.of(
                        new Role("ROLE_USER", ""),
                        new Role("ROLE_ADMIN", "")
                ))
                .build();

        UserAuthDetails userAuthDetails = new UserAuthDetails(user);
        UserAuthDetails adminAuthDetails = new UserAuthDetails(admin);

        return new InMemoryUserDetailsManager(List.of(userAuthDetails, adminAuthDetails));
    }
}
