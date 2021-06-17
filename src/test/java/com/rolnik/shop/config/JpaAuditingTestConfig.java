package com.rolnik.shop.config;

import com.rolnik.shop.model.entities.User;
import com.rolnik.shop.security.UserAuthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@TestConfiguration
@EnableJpaAuditing(auditorAwareRef = "testAuditorProvider")
public class JpaAuditingTestConfig {
    @Bean
    public AuditorAware<User> testAuditorProvider() {
        return Optional::empty;
    }
}
