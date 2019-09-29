package edu.ameier.hockey.dto.nhlStandings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamStandingDto {
    private int id;
    private String name;
    private String link;
}
