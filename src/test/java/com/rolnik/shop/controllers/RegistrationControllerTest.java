package com.rolnik.shop.controllers;

import com.rolnik.shop.dtos.user.UserCreateRequest;
import com.rolnik.shop.model.entities.User;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Import(RegistrationController.class)
class RegistrationControllerTest extends BaseControllerTest {

    @Test
    void whenRegisterUser_thenReturnUserJson() throws Exception {
        //given
        User user = new User(
                "username",
                "test@test.pl",
                "test",
                Sets.newHashSet()
        );
        UserCreateRequest userCreateRequest = new UserCreateRequest(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
        //when
        Mockito.when(userService.registerUser(Mockito.any())).thenReturn(user);
        //then
        mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.toJson(userCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("username"))
        );
    }
}