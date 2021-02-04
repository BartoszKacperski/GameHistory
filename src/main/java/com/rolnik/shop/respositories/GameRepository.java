package com.rolnik.shop.respositories;

import com.rolnik.shop.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
