package edu.ameier.hockey.dto.nhlTeam;

import edu.ameier.hockey.dto.nhlPlayer.NHLPlayersDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTeamDto {
    private int id;
    private boolean favorite;
    private String name;
    private String firstYearOfPlay;
    private String venue;
    private String division;
    private int winNums;
    private int lossNums;
    private int otNums;
    private int ptsNums;
    private float goalsPerGameNums;
    private float goalsAgainstPerGameNums;
    private float shotsPerGameNums;
    private float shotsAllowedPerGameNums;
    private float powerPlayPct;
    private float penaltyKillPct;
    private float savePct;
    String winsRank;
    String lossesRank;
    String ptsRank;
    String goalsPerGameRank;
    String goalsAgainstPerGameRank;
    String shotsPerGameRank;
    String powerPlayRank;
    String penaltyKillRank;
    String faceOffsRank;
    List<NHLPlayersDto> roster;
}
