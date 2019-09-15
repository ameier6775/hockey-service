package edu.ameier.hockey.dto.nhlPlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatsStatDto {
    private String timeOnIce;
    private int assists;
    private int goals;
    private int pim;
    private int shots;
    private int games;
    private int hits;
    private int powerPlayGoals;
    private int powerPlayPoints;
    private String powerPlayTimeOnIce;
    private String evenTimeOnIce;
    private String penaltyMinutes;
    private float faceOffPct;
    private float shotPct;
    private int gameWinningGoals;
    private int overTimeGoals;
    private int shortHandedGoals;
    private int shortHandedPoints;
    private String shortHandedTimeOnIce;
    private int blocked;
    private int plusMinus;
    private int points;
    private int shifts;
    private String timeOnIcePerGame;
    private String evenTimeOnIcePerGame;
    private String shortHandedTimeOnIcePerGame;
    private String powerPlayTimeOnIcePerGame;

}
