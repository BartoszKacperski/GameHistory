package com.rolnik.shop.respositories;

import com.rolnik.shop.model.entities.Game;
import com.rolnik.shop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByCurrentGame(Game game);
}
