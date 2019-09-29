package edu.ameier.hockey.dto.nhlStandings;

import lombok.Data;

@Data
public class StreakDto {
    private String streakType;
    private int streakNumber;
    private String streakCode;
}
