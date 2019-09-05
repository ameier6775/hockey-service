package edu.ameier.hockey.dto;

import lombok.Data;

import java.util.List;

@Data
public class NHLTeamResponseDto {
    private String copyright;

    private List<NHLTeamDto> teams;

    private  Boolean favorite;
}
