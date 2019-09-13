package edu.ameier.hockey.dto.nhlTeam;

import edu.ameier.hockey.dto.nhlPlayer.NHLRosterDto;
import lombok.Data;

import java.util.List;


@Data
public class NHLTeamDto {
    private long id;
    private String name;
    private String link;
    private NHLVenueDto venue;
    private String abbreviation;
    private String teamName;
    private String locationName;
    private long firstYearOfPlay;
    private NHLTeamDivisionDto division;
    private NHLTeamConferenceDto conference;
    private String shortName;
    private String officialSiteUrl;
    private long franchiseId;
    private List<NHLTeamStatsDto> nhlTeamStatsDto;
    private NHLRosterDto roster;
}
