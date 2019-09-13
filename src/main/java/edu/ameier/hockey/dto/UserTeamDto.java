package edu.ameier.hockey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    String winsRank;
    String lossesRank;
    String ptsRank;
    String goalsPerGameRank;
    String goalsAgainstPerGameRank;
    String shotsPerGameRank;
    String powerPlayRank;
    String penaltyKillRank;
    String savePctgRank;
    String faceOffsRank;
}
