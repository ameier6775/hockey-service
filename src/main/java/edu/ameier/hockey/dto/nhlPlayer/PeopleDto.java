package edu.ameier.hockey.dto.nhlPlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleDto {
    private int id;
    private String fullName;
    private String link;
    private String firstName;
    private String lastName;
    private String primaryNumber;
    private String birthDate;
    private int currentAge;
    private String birthCity;
    private String birthStateProvince;
    private String birthCountry;
    private String nationality;
    private String height;
    private int weight;
    private boolean active;
    private boolean alternateCaptain;
    private boolean captain;
    private boolean rookie;
    private String shootsCatches;
    private String rosterStatus;
    private PlayerTeamDto currentTeam;
    private PlayerPositionDto primaryPosition;
}
