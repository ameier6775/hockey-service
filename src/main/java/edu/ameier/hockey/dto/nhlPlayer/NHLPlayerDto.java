package edu.ameier.hockey.dto.nhlPlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NHLPlayerDto {
    private int id;
    private String fullName;
    private String link;
}
