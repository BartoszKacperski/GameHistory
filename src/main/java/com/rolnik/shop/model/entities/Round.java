package com.rolnik.shop.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rolnik.shop.model.entities.base.SimpleEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "playerRounds")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "playerRounds")

@Entity(name = "rounds")
public class Round extends SimpleEntity {
    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Game game;
    @OneToMany(mappedBy = "round", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PlayerRound> playerRounds;
}
