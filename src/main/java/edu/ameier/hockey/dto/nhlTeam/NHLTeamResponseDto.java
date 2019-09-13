package edu.ameier.hockey.dto.nhlTeam;

import lombok.Data;

import java.util.List;

@Data
public class NHLTeamResponseDto {
    private String copyright;

    private List<NHLTeamDto> teams;
}
