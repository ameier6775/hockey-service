package edu.ameier.hockey.dto.nhlStandings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandingResponseDto {
    private TeamStandingDto team;
    private LeagueRecordDto leagueRecord;
    private int goalsAgainst;
    private int goalsScored;
    private int points;
    private String divisionRank;
    private String conferenceRank;
    private String leagueRank;
    private String wildCardRank;
    private int row;
    private int gamesPlayed;
    private StreakDto streak;
}
