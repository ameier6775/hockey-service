package edu.ameier.hockey.dto.nhl;

import edu.ameier.hockey.dto.nhl.NHLTeamDto;
import lombok.Data;

import java.util.List;

@Data
public class NHLTeamResponseDto {
    private String copyright;

    private List<NHLTeamDto> teams;
}
