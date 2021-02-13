package com.rolnik.shop.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rolnik.shop.model.entities.base.SimpleEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "rounds")
@ToString(exclude = "rounds")
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "games")
public class Game extends SimpleEntity {
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Round> rounds;
    private boolean finished = false;
    @OneToOne(mappedBy = "currentGame")
    private User user;

    public void addRound(Round round) {
        rounds.add(round);
        round.setGame(this);
    }
}
