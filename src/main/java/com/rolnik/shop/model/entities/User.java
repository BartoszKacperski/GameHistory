package com.rolnik.shop.model.entities;

import com.rolnik.shop.model.entities.base.SimpleEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "users")
public class User extends SimpleEntity {
    @Size(min = 3, max = 255)
    @Column(unique=true)
    private String username;
    @Size(min = 3, max = 255)
    @Email
    @Column(unique=true)
    private String email;
    @Size(min = 5, max = 255)
    private String password;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_game_id")
    private Game currentGame;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public void setCurrentGame(Game game) {
        this.currentGame = game;
    }

    public void resetCurrentGame() {
        this.currentGame = null;
    }
}
