package edu.ameier.hockey.dto.nhlTeam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamFavorite {
    private Long teamId;
    private Long userId;
}
