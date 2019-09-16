package edu.ameier.hockey.dto.nhlTeam;

import lombok.Data;

@Data
public class NHLTeamStatsStatDto {
    String wins;
    String losses;
    String ot;
    String pts;
    String goalsPerGame;
    String goalsAgainstPerGame;
    String shotsPerGame;
    String shotsAllowed;
    String powerPlayPercentage;
    String penaltyKillPercentage;
    String savePctg;
    String faceOffWinPercentage;

}
