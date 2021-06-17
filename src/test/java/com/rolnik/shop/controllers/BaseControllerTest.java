package com.rolnik.shop.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rolnik.shop.BaseTest;
import com.rolnik.shop.config.BCryptPasswordEncoderConfig;
import com.rolnik.shop.config.JpaAuditingTestConfig;
import com.rolnik.shop.config.SecurityTestConfig;
import com.rolnik.shop.security.JWTConfig;
import com.rolnik.shop.security.SecurityConfig;
import com.rolnik.shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

@WebMvcTest
@ContextConfiguration(classes = {
        SecurityTestConfig.class,
        BCryptPasswordEncoderConfig.class,
        SecurityConfig.class,
        JWTConfig.class,
        ModelMapper.class,
        JpaAuditingTestConfig.class
})
public class BaseControllerTest extends BaseTest {
    @MockBean
    protected UserService userService;
    @Autowired
    protected MockMvc mvc;

    byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());

        return mapper.writeValueAsBytes(object);
    }
}
