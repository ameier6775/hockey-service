package edu.ameier.hockey.dto.nhlTeam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NHLTeamFranchiseDto {
    private long franchiseId;
    private String teamName;
}
