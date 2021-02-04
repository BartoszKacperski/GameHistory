package com.rolnik.shop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rolnik.shop.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JWTConfig jwtConfig;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;

        AntPathRequestMatcher pathMatcher = new AntPathRequestMatcher(jwtConfig.getPath(), "POST");
        super.setRequiresAuthenticationRequestMatcher(pathMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        try {
            User credentials = new ObjectMapper().readValue(req.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword(),
                    Collections.emptyList()
            );

            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        long now = System.currentTimeMillis();

        UserAuthDetails userAuthDetails = (UserAuthDetails) auth.getPrincipal();

        String token = JWT.create()
                .withSubject(userAuthDetails.getUsername())
                .withClaim("authorities",
                        userAuthDetails
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
                )
                .withClaim("userId", userAuthDetails.getId())
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + jwtConfig.getExpiration() * 1000))
                .sign(Algorithm.HMAC512(jwtConfig.getSecret().getBytes()));

        res.getWriter().write(token);
        res.getWriter().flush();
    }
}
