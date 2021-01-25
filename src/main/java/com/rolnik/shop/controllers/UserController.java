package com.rolnik.shop.controllers;

import com.rolnik.shop.model.Game;
import com.rolnik.shop.model.Player;
import com.rolnik.shop.model.Round;
import com.rolnik.shop.model.User;
import com.rolnik.shop.respositories.GameRepository;
import com.rolnik.shop.respositories.PlayerRepository;
import com.rolnik.shop.respositories.RoundRepository;
import com.rolnik.shop.respositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor

@Controller
@RequestMapping(path = "users")
public class UserController {

    private final UserRepository userRepository;


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
