package edu.ameier.hockey.dto.nhl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NHLTeamStatsDto {
    private NHLTeamStatsTypeDto type;
    private List<NHLTeamStatsSplitsDto> splits;

}
