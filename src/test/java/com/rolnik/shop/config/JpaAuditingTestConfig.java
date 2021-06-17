package com.rolnik.shop.config;

import com.rolnik.shop.model.entities.User;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@TestConfiguration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingTestConfig {
    @Bean
    public AuditorAware<User> auditorProvider() {
        return () -> Optional.of(new User());
    }
}
