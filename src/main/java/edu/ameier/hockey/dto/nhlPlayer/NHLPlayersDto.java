package edu.ameier.hockey.dto.nhlPlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NHLPlayersDto {
    private NHLPlayerDto person;
    private String jerseyNumber;
    private NHLPositionDto position;
}
