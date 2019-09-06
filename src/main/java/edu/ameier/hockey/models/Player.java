package edu.ameier.hockey.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "player")
public class Player {

    @Id
    @Column(name = "player_Id", unique = true)
    protected Long playerId;

}
