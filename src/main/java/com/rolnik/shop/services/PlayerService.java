package com.rolnik.shop.services;

import com.rolnik.shop.exceptions.EntityNotFoundException;
import com.rolnik.shop.model.entities.Player;
import com.rolnik.shop.model.entities.PlayerRound;
import com.rolnik.shop.respositories.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor

@Service
@Validated
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player create(@Valid Player player) {
        return playerRepository.save(player);
    }

    public Player getById(long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Player.class));
    }

    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    public Player update(@Valid Player player) {
        return playerRepository.save(player);
    }

    public void delete(long id) {
        playerRepository.deleteById(id);
    }
}
