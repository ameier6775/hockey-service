package edu.ameier.hockey.dto.nhlPlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResponseDto {
    private int assists;
    private int goals;
    private int pim;
    private int shots;
    private int games;
    private int powerPlayGoals;
    private int powerPlayPoints;
    private String penaltyMinutes;
    private float shotPct;
    private int gameWinningGoals;
    private int overTimeGoals;
    private int shortHandedGoals;
    private int shortHandedPoints;
    private int plusMinus;
    private int points;
    // 2018-2019 Stats
    private int lastYearGoals;
    private int lastYearAssists;
    private int lastYearPoints;
    private int lastYearPowerPlayPoints;
    private int lastYearShotsOnGoal;
    private int lastYearPlusMinus;
    private int lastYearHits;
    private int lastYearBlocks;
    // 2018-2019 Stats end
    private int id;
    private String fullName;
    private String primaryNumber;
    private int currentAge;
    private String birthDate;
    private String birthCity;
    private String birthStateProvince;
    private String birthCountry;
    private String nationality;
    private String height;
    private int weight;
    private boolean alternateCaptain;
    private boolean captain;
    private boolean rookie;
    private String shootsCatches;
    private String currentTeam;
    private String position;
    private int blocks;
    private String timeOnIcePerGame;
    private int hits;
    private boolean favorite;
}
