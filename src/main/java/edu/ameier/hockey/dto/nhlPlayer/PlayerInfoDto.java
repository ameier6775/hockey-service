package edu.ameier.hockey.dto.nhlPlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInfoDto {
    private String copyright;
    private List<PeopleDto> people;
}
