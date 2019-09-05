package edu.ameier.hockey.dto;

import lombok.Data;

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
    private NHLConferenceDto conference;
    private String shortName;
    private String officialSiteUrl;
    private long franchiseId;
}
