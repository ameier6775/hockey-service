package edu.ameier.hockey.dto.nhlPlayer;

import edu.ameier.hockey.dto.nhlPlayer.NHLPlayersDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NHLRosterDto {
    List<NHLPlayersDto> roster;
    private String link;
}
