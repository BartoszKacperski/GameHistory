package com.rolnik.shop.services;

import com.rolnik.shop.model.Player;
import com.rolnik.shop.respositories.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player create(Player player) {
        return playerRepository.save(player);
    }

    public Player getById(long id) {
        return playerRepository.findById(id)
                //TODO Change exception
                .orElseThrow(() -> new RuntimeException("playerNotFound"));
    }

    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    public Player update(Player player) {
        return playerRepository.save(player);
    }

    public void delete(long id) {
        playerRepository.deleteById(id);
    }
}
