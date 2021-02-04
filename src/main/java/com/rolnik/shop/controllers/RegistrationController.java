package com.rolnik.shop.controllers;

import com.rolnik.shop.dto.UserCreate;
import com.rolnik.shop.dto.UserResponse;
import com.rolnik.shop.model.User;
import com.rolnik.shop.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor

@RestController
public class RegistrationController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping(
            value = "/registration",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody UserCreate userCreate){
        User user = userService.registerUser(mapUser(userCreate));

        return mapUser(user);
    }


    private User mapUser(UserCreate userCreate) {
        return modelMapper.map(userCreate, User.class);
    }

    private UserResponse mapUser(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
