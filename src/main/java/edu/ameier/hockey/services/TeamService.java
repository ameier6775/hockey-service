package edu.ameier.hockey.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ameier.hockey.dto.NHLTeamResponseDto;
import edu.ameier.hockey.dto.TeamFavorite;
import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.models.HockeyTeam;
import edu.ameier.hockey.repositories.TeamRepository;
import edu.ameier.hockey.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.ameier.hockey.security.SecurityConstants.*;

@Service
@Slf4j
public class TeamService {
    private RestTemplateService restTemplateService;
    private ObjectMapper mapper;
    private UserRepository userRepository;
    private TeamRepository teamRepository;

    public TeamService(RestTemplateService restTemplateService, ObjectMapper mapper, UserRepository userRepository, TeamRepository teamRepository) {
        this.restTemplateService = restTemplateService;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
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

    public String getTeamStats(Long id) {
        String teamId = id.toString();
        final String url = "http://statsapi.web.nhl.com/api/v1/teams/" + teamId + "/stats";
        return restTemplateService.getHttpRestResponse(url);
    }

    public String getCup() {
        final String url = "http://records.nhl.com/site/api/trophy";
        return restTemplateService.getHttpRestResponse(url);
    }

    public String getTeamById(Long id) {
        String teamId = id.toString();
        final String url = "http://statsapi.web.nhl.com/api/v1/teams/" + teamId;
        return restTemplateService.getHttpRestResponse(url);
    }

    public String getTeamRosterById(Long id) {
        String rosterId = id.toString();
        final String url = "http://statsapi.web.nhl.com/api/v1/teams/" + rosterId + "?expand=team.roster";
        return restTemplateService.getHttpRestResponse(url);
    }

    public AppUser addTeamToFavorites(TeamFavorite teamFavorite) {
        AppUser appuser = userRepository.findById(teamFavorite.getUserId()).orElseThrow(RuntimeException::new);

        if(appuser.getTeamIds().isEmpty() && teamRepository.existsById(teamFavorite.getTeamId())) {
            List<HockeyTeam> favorites = new ArrayList<>();
            HockeyTeam hockeyTeam = teamRepository.getOne(teamFavorite.getTeamId());
            favorites.add(hockeyTeam);
            appuser.setTeamIds(favorites);
            return userRepository.save(appuser);
        }

        else if (teamRepository.existsById(teamFavorite.getTeamId())) {
            List<HockeyTeam> favorites = appuser.getTeamIds();
            HockeyTeam hockeyTeam = teamRepository.getOne(teamFavorite.getTeamId());
            favorites.add(hockeyTeam);
            appuser.setTeamIds(favorites);
            return userRepository.save(appuser);

        } else if (appuser.getTeamIds().isEmpty()) {
            List<HockeyTeam> favorites = new ArrayList<>();
            HockeyTeam favorite = new HockeyTeam();
            favorite.setTeamId(teamFavorite.getTeamId());
            teamRepository.save(favorite);
            favorites.add(favorite);
            appuser.setTeamIds(favorites);
            return userRepository.save(appuser);
        }
        else {
            List<HockeyTeam> favorites = appuser.getTeamIds();
            HockeyTeam favorite = new HockeyTeam();
            favorite.setTeamId(teamFavorite.getTeamId());
            teamRepository.save(favorite);
            favorites.add(favorite);
            appuser.setTeamIds(favorites);
            return userRepository.save(appuser);
        }
    }

    public AppUser removeTeamFromFavorites(TeamFavorite teamFavorite) {
        AppUser appuser = userRepository.findById(teamFavorite.getUserId()).orElseThrow(RuntimeException::new);
        List<HockeyTeam> favorites = appuser.getTeamIds();
        HockeyTeam favorite = teamRepository.getOne(teamFavorite.getTeamId());
        favorites.remove(favorite);
        teamRepository.delete(favorite);
        appuser.setTeamIds(favorites);
        userRepository.save(appuser);
        return appuser;
    }

    public List<HockeyTeam> getUserTeams(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token == null)
        {
            throw new RuntimeException("token is null");
        }
        SecretKey key = new SecretKeySpec(SECRET.getBytes(), "HmacSHA512");
        // Parsing the token
        String userName = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        AppUser appUser = userRepository.findByUserName(userName);
        return appUser.getTeamIds();
    }
}