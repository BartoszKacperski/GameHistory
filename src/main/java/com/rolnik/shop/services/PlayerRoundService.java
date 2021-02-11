package com.rolnik.shop.services;

import com.rolnik.shop.exceptions.EntityNotFoundException;
import com.rolnik.shop.model.entities.PlayerRound;
import com.rolnik.shop.respositories.PlayerRoundRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@AllArgsConstructor

@Service
public class PlayerRoundService {
    private PlayerRoundRepository playerRoundRepository;

    public PlayerRound getById(Long id) {
        return playerRoundRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PlayerRound.class));
    }

    @Transactional
    public PlayerRound updatePoint(Long id, BigDecimal point) {
        PlayerRound playerRound = getById(id);

        playerRound.setPoint(point);

        return playerRound;
    }
}
