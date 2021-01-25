package com.rolnik.shop.respositories;

import com.rolnik.shop.model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}
