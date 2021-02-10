package com.rolnik.shop.controllers;

import com.rolnik.shop.dto.UserCreateRequest;
import com.rolnik.shop.dto.UserCreateResponse;
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
    public UserCreateResponse registerUser(@RequestBody UserCreateRequest userCreateRequest){
        User user = userService.registerUser(mapUser(userCreateRequest));

        return mapUser(user);
    }


    private User mapUser(UserCreateRequest userCreateRequest) {
        return modelMapper.map(userCreateRequest, User.class);
    }

    private UserCreateResponse mapUser(User user) {
        return modelMapper.map(user, UserCreateResponse.class);
    }
}
