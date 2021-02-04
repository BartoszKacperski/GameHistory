package com.rolnik.shop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rolnik.shop.respositories.UserRepository;
import com.rolnik.shop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTConfig jwtConfig;
    private UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JWTConfig jwtConfig, UserDetailsService userDetailsService) {
        super(authManager);
        this.jwtConfig = jwtConfig;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(jwtConfig.getHeader());

        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            chain.doFilter(req, res);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e){
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getHeader());

        if (token != null) {
            // parse the token.
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtConfig.getSecret().getBytes()))
                    .build()
                    .verify(token.replace(jwtConfig.getPrefix() + ": ", ""));

            String username = decodedJWT.getSubject();
            List<String> authorities = decodedJWT.getClaim("authorities").asList(String.class);

            if (username != null) {
                return new UsernamePasswordAuthenticationToken(
                        userDetailsService.loadUserByUsername(username),
                        null,
                        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
            }

            return null;
        }

        return null;
    }
}
