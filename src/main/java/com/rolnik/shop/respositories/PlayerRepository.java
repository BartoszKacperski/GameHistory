package com.rolnik.shop.respositories;

import com.rolnik.shop.model.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
