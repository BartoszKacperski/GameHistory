package com.rolnik.shop.controllers;

import com.rolnik.shop.model.User;
import com.rolnik.shop.respositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public @ResponseBody String createUser(){
        User user = new User();

        return userRepository.save(user).toString();
    }

    @GetMapping
    public @ResponseBody Iterable<User> getAll() {
        return userRepository.findAll();
    }

}
