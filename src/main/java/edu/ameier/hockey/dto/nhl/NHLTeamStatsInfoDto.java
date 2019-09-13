package edu.ameier.hockey.dto.nhl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NHLTeamStatsInfoDto {
    private String copyright;
    private List<NHLTeamStatsTeamDto> teams;
    private boolean favorite;
}
