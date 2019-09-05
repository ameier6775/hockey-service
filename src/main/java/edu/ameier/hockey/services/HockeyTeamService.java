package edu.ameier.hockey.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ameier.hockey.dto.NHLTeamResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class HockeyTeamService {
    private RestTemplateService restTemplateService;
    private ObjectMapper mapper;

    public HockeyTeamService(RestTemplateService restTemplateService, ObjectMapper mapper) {
        this.restTemplateService = restTemplateService;
        this.mapper = mapper;
    }

    public String getTeams() {
        final String url = "http://statsapi.web.nhl.com/api/v1/teams";
        String response = restTemplateService.getHttpRestResponse(url);
        try {

        NHLTeamResponseDto teams = mapper.readValue(response, NHLTeamResponseDto.class);
        log.info(teams.toString());
        }
        catch (IOException exception)
        {
            log.error(exception.getMessage());

        }
        return restTemplateService.getHttpRestResponse(url);
    }

    public String getCup()
    {
        final String url = "http://records.nhl.com/site/api/trophy";
        return restTemplateService.getHttpRestResponse(url);
    }

    public String getTeamById(Long id) {
        String teamId = id.toString();
        final String url = "http://statsapi.web.nhl.com/api/v1/teams/" + teamId;
        return restTemplateService.getHttpRestResponse(url);
    }
    
    public String getTeamRosterById(Long id) {
        String teamId = id.toString();
        final String url = "http://statsapi.web.nhl.com/api/v1/teams/" + teamId + "?expand=team.roster";
        return restTemplateService.getHttpRestResponse(url);
    }

}




// public String getSeasonStatsForPlayer(Long id, Long anotherId) {
    //     String playerId = id.toString();
    //     String seasonId = anotherId.toString();
    //     final String url = "https://statsapi.web.nhl.com/api/v1/people/" + playerId + "/stats?stats=yearByYear";
    //     return getHttpRestResponse(url);
    // }

//    private String getHttpRestResponse(String url)
//    {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        ResponseEntity<String> response = restTemplate.exchange(url,
//                HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
//
//        return response.getBody();
//    }
