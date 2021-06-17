package com.rolnik.shop.config;

import com.rolnik.shop.model.entities.User;
import com.rolnik.shop.security.UserAuthDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfig {
    @Bean
    public AuditorAware<User> auditorProvider() {
        return () -> {
            if (SecurityContextHolder.getContext() == null ||
                SecurityContextHolder.getContext().getAuthentication() == null) {
                return Optional.empty();
            }
            UserAuthDetails userAuthDetails = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication();

            return Optional.ofNullable(userAuthDetails.getUser());
        };
    }
}
