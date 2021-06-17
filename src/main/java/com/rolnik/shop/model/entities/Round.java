package com.rolnik.shop.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rolnik.shop.model.entities.base.SimpleEntityWithDates;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = "playerRounds")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "playerRounds")

@Entity(name = "rounds")
public class Round extends SimpleEntityWithDates {
    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Game game;
    @OneToMany(mappedBy = "round", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PlayerRound> playerRounds;

    public void addPlayerRound(PlayerRound playerRound) {
        playerRounds.add(playerRound);
        playerRound.setRound(this);
    }
}
