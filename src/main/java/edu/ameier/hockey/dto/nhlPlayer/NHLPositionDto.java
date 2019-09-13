package edu.ameier.hockey.dto.nhlPlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NHLPositionDto {
    private String code;
    private String name;
    private String type;
    private String abbreviation;
}
