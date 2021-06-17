package com.rolnik.shop.respositories;

import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> getAllByFinished(boolean finished);
}
