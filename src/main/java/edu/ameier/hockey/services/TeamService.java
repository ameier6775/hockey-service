package edu.ameier.hockey.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ameier.hockey.dto.UserTeamDto;
import edu.ameier.hockey.dto.nhlPlayer.NHLRosterDto;
import edu.ameier.hockey.dto.nhlTeam.*;
import edu.ameier.hockey.dto.TeamFavorite;
import edu.ameier.hockey.dto.UserTeamsDto;
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
import java.util.stream.Collectors;

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

    public List<UserTeamsDto> getTeams(HttpServletRequest request) {
        final String url = "http://statsapi.web.nhl.com/api/v1/teams";
        String response = restTemplateService.getHttpRestResponse(url);

        AppUser appUser = getAppUserFromRequest(request);
        List<UserTeamsDto> userTeams = new ArrayList<>();
        try {

            NHLTeamResponseDto teams = mapper.readValue(response, NHLTeamResponseDto.class);
            log.info(teams.toString());
            userTeams = teams.getTeams().stream().map(team -> {
                UserTeamsDto userTeamsDto = new UserTeamsDto();
                userTeamsDto.setId(team.getId());
                userTeamsDto.setName(team.getName());
                userTeamsDto.setOfficialSiteUrl(team.getOfficialSiteUrl());
                return userTeamsDto;
            }).collect(Collectors.toList());

            for (UserTeamsDto team:
                userTeams ) {
                for (HockeyTeam userModelTeam:
                     appUser.getTeamIds()) {
                    if(team.getId() == userModelTeam.getTeamId()) {
                        team.setFavorite(true);
                    }
                }
            }

        }
        catch (IOException exception)
        {
            log.error(exception.getMessage());

        }

        return userTeams;
    }

//    public String getTeamStats(Long id) {
//        String teamId = id.toString();
////        https://statsapi.web.nhl.com/api/v1/teams/4?expand=team.stats
//        final String url = "http://statsapi.web.nhl.com/api/v1/teams/" + teamId + "/stats";
//        return restTemplateService.getHttpRestResponse(url);
//    }

    public String getCup() {
        final String url = "http://records.nhl.com/site/api/trophy";
        return restTemplateService.getHttpRestResponse(url);
    }

    public UserTeamDto getTeamById(Long id, HttpServletRequest request) {
        String teamId = id.toString();
        final String teamStatsUrl = "http://statsapi.web.nhl.com/api/v1/teams/" + teamId + "?expand=team.stats";
        String teamStatsResponse = restTemplateService.getHttpRestResponse(teamStatsUrl);
        final String rosterUrl = "http://statsapi.web.nhl.com/api/v1/teams/" + teamId + "?expand=team.roster";
        String teamRoster = restTemplateService.getHttpRestResponse(rosterUrl);

        AppUser appUser = getAppUserFromRequest(request);
        NHLTeamStatsInfoDto team = new NHLTeamStatsInfoDto();
        NHLTeamResponseDto withRoster = new NHLTeamResponseDto();

        try {
            team = mapper.readValue(teamStatsResponse, NHLTeamStatsInfoDto.class);
            withRoster = mapper.readValue(teamRoster, NHLTeamResponseDto.class);
            log.info(withRoster.toString());

        }
        catch (IOException exception)
        {
            log.error(exception.getMessage());
        }

        UserTeamDto userTeam = new UserTeamDto();
        
        NHLTeamStatsTeamDto statTeam  = team.getTeams().get(0);
        NHLTeamStatsStatDto teamsLatestStats = statTeam.getTeamStats().get(0).getSplits().get(0).getStat();
        NHLTeamStatsStatDto teamsSeasonRankingStats = statTeam.getTeamStats().get(0).getSplits().get(1).getStat();

        NHLRosterDto roster = withRoster.getTeams().get(0).getRoster();

        userTeam.setRoster(roster.getRoster());

        userTeam.setId(statTeam.getId());
        userTeam.setName(statTeam.getName());
        userTeam.setDivision(statTeam.getDivision().getName());
        userTeam.setVenue(statTeam.getVenue().getName());
        userTeam.setFirstYearOfPlay(statTeam.getFirstYearOfPlay());

        userTeam.setWinsRank(teamsSeasonRankingStats.getWins());
        userTeam.setLossesRank(teamsSeasonRankingStats.getLosses());
        userTeam.setPtsRank(teamsSeasonRankingStats.getPts());
        userTeam.setGoalsPerGameRank(teamsSeasonRankingStats.getGoalsPerGame());
        userTeam.setGoalsAgainstPerGameRank(teamsSeasonRankingStats.getGoalsAgainstPerGame());
        userTeam.setShotsPerGameRank(teamsSeasonRankingStats.getShotsPerGame());
        userTeam.setPowerPlayRank(teamsSeasonRankingStats.getPowerPlayPercentage());
        userTeam.setPenaltyKillRank(teamsSeasonRankingStats.getPenaltyKillPercentage());
        userTeam.setSavePctgRank(teamsSeasonRankingStats.getSavePercentage());
        userTeam.setFaceOffsRank(teamsSeasonRankingStats.getFaceOffWinPercentage());

        userTeam.setWinNums(Integer.parseInt(teamsLatestStats.getWins()));
        userTeam.setLossNums(Integer.parseInt(teamsLatestStats.getLosses()));
        userTeam.setOtNums(Integer.parseInt(teamsLatestStats.getOt()));
        userTeam.setPtsNums(Integer.parseInt(teamsLatestStats.getPts()));
        userTeam.setGoalsPerGameNums(Float.parseFloat(teamsLatestStats.getGoalsPerGame()));
        userTeam.setGoalsAgainstPerGameNums(Float.parseFloat(teamsLatestStats.getGoalsAgainstPerGame()));
        userTeam.setShotsPerGameNums(Float.parseFloat(teamsLatestStats.getShotsPerGame()));
        userTeam.setShotsAllowedPerGameNums(Float.parseFloat(teamsLatestStats.getShotsAllowed()));

        return userTeam;
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

//    public List<HockeyTeam> getUserTeams(HttpServletRequest request) {
//        String token = request.getHeader(HEADER_STRING);
//
//        if (token == null)
//        {
//            throw new RuntimeException("token is null");
//        }
//        SecretKey key = new SecretKeySpec(SECRET.getBytes(), "HmacSHA512");
//        // Parsing the token
//        String userName = Jwts.parser()
//                .setSigningKey(key)
//                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//                .getBody()
//                .getSubject();
//
//        AppUser appUser = userRepository.findByUserName(userName);
//        return appUser.getTeamIds();
//    }

    private AppUser getAppUserFromRequest(HttpServletRequest request)
    {
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

        return userRepository.findByUserName(userName);
    }
}