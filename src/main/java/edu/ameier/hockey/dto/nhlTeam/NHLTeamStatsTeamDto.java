package edu.ameier.hockey.dto.nhlTeam;

import lombok.Data;

import java.util.List;

@Data
public class NHLTeamStatsTeamDto {
    private int id;
    private String name;
    private String link;
    private NHLVenueDto venue;
    private String abbreviation;
    private String firstYearOfPlay;
    private NHLTeamDivisionDto division;
    private NHLTeamConferenceDto conference;
    private NHLTeamFranchiseDto franchise;
    private List<NHLTeamStatsDto> teamStats;
    private String shortName;
    private String officialSiteUrl;
//    private int franchiseId;
    private boolean active;
}
