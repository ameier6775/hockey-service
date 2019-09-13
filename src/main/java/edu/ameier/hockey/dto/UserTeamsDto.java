package edu.ameier.hockey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTeamsDto {
    private long id;
    private String name;
    private String officialSiteUrl;
    private boolean favorite;
}
