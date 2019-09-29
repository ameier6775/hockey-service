package edu.ameier.hockey.dto.nhlStandings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueRecordDto {
    private int wins;
    private int losses;
    private int ot;
    private String type;
}
