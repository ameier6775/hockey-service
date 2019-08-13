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
@Table(name = "team")
public class HockeyTeam {

    @Id
    @Column(name = "team_Id", unique = true)
    private Long id;

}
