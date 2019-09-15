package edu.ameier.hockey.dto.nhlTeam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonRootName("teams")
@JsonIgnoreProperties(value = {"copyright"}, ignoreUnknown = true)
@JsonTypeName("teams")
public class TeamDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("conference")
    private String conference;

    @JsonProperty("locationName")
    private String location;

    @JsonProperty("venue")
    private String venue;

    @JsonProperty("officialSiteUrl")
    private String website;
}
