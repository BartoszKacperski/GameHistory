package com.rolnik.shop.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rolnik.shop.BaseTest;
import com.rolnik.shop.config.BCryptPasswordEncoderConfig;
import com.rolnik.shop.config.SecurityTestConfig;
import com.rolnik.shop.security.JWTConfig;
import com.rolnik.shop.security.SecurityConfig;
import com.rolnik.shop.services.UserService;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {SecurityTestConfig.class, BCryptPasswordEncoderConfig.class, SecurityConfig.class, JWTConfig.class, ModelMapper.class})
public class BaseControllerTest extends BaseTest {
    @MockBean(name = "userService")
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

    protected String getUserAuthToken() throws Exception {
        JSONObject userAuthBody = new JSONObject()
                .accumulate("username", "user")
                .accumulate("password", "password");

        return getAuthToken(userAuthBody);
    }

    protected String getAdminAuthToken() throws Exception {
        JSONObject adminAuthBody = new JSONObject()
                .accumulate("username", "admin")
                .accumulate("password", "password");

        return getAuthToken(adminAuthBody);
    }

    private String getAuthToken(JSONObject authBody) throws Exception {
        ResultActions result = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authBody.toString()))
                .andExpect(status().isOk());

        return "Bearer: " + result.andReturn().getResponse().getContentAsString();
    }
}
