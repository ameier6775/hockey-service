package edu.ameier.hockey.services;

import edu.ameier.hockey.dto.PlayerFavorite;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ameier.hockey.dto.nhlPlayer.*;
import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.models.Player;
import edu.ameier.hockey.repositories.PlayerRepository;
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

@Slf4j
@Service
public class PlayerService {
    private RestTemplateService restTemplateService;
    private ObjectMapper mapper;
    private UserRepository userRepository;
    private PlayerRepository playerRepository;

    public PlayerService(RestTemplateService restTemplateService, UserRepository userRepository, PlayerRepository playerRepository, ObjectMapper mapper) {
        this.restTemplateService = restTemplateService;
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.mapper = mapper;
    }

    public PlayerResponseDto getPlayerStats(Long id, HttpServletRequest request) {
        String playerId = id.toString();
        final String generalInfoUrl = "http://statsapi.web.nhl.com/api/v1/people/" + playerId;
        String playerInfoResponse = restTemplateService.getHttpRestResponse(generalInfoUrl);
        final String statsUrl = "http://statsapi.web.nhl.com/api/v1/people/" + playerId + "/stats?stats=statsSingleSeason&season=20192020";
        String playerStatsResponse = restTemplateService.getHttpRestResponse(statsUrl);
        final String lastYearStatsUrl = "http://statsapi.web.nhl.com/api/v1/people/" + playerId + "/stats?stats=statsSingleSeason&season=20182019";
        String lastYearStatsResponse = restTemplateService.getHttpRestResponse(lastYearStatsUrl);

        AppUser appUser = getAppUserFromRequest(request);
        PlayerStatsGeneralDto playerStats = new PlayerStatsGeneralDto();
        PlayerStatsGeneralDto lastYearStats = new PlayerStatsGeneralDto();
        PlayerInfoDto playerInfo = new PlayerInfoDto();

        try {
            playerInfo = mapper.readValue(playerInfoResponse, PlayerInfoDto.class);
            playerStats = mapper.readValue(playerStatsResponse, PlayerStatsGeneralDto.class);
            lastYearStats = mapper.readValue(lastYearStatsResponse, PlayerStatsGeneralDto.class);
            log.info(playerStats.toString());
        }

        catch (IOException exception)
        {
            log.error(exception.getMessage());
        }

        PlayerResponseDto player = new PlayerResponseDto();

        PeopleDto info = playerInfo.getPeople().get(0);
        PlayerStatsStatDto stats = playerStats.getStats().get(0).getSplits().get(0).getStat();
        PlayerStatsStatDto pastStats = lastYearStats.getStats().get(0).getSplits().get(0).getStat();

        for (Player playerFav: appUser.getPlayerIds()) {
            if (playerFav.getPlayerId() == info.getId()) {
                player.setFavorite(true);
            }
        }

        player.setId(info.getId());
        player.setFullName(info.getFullName());
        player.setPrimaryNumber(info.getPrimaryNumber());
        player.setCurrentTeam(info.getCurrentTeam().getName());
        player.setPosition(info.getPrimaryPosition().getName());
        player.setCaptain(info.isCaptain());
        player.setAlternateCaptain(info.isAlternateCaptain());
        player.setBirthDate(info.getBirthDate());
        player.setBirthCity(info.getBirthCity());
        player.setBirthCountry(info.getBirthCountry());
        player.setBirthStateProvince(info.getBirthStateProvince());
        player.setCurrentAge(info.getCurrentAge());
        player.setNationality(info.getNationality());
        player.setHeight(info.getHeight());
        player.setWeight(info.getWeight());
        player.setRookie(info.isRookie());
        player.setShootsCatches(info.getShootsCatches());

        player.setGames(stats.getGames());
        player.setGoals(stats.getGoals());
        player.setAssists(stats.getAssists());
        player.setPoints(stats.getPoints());
        player.setHits(stats.getHits());
        player.setTimeOnIcePerGame(stats.getTimeOnIcePerGame());
        player.setPim(stats.getPim());
        player.setBlocks(stats.getBlocks());
        player.setShots(stats.getShots());
        player.setPowerPlayGoals(stats.getPowerPlayGoals());
        player.setPowerPlayPoints(stats.getPowerPlayPoints());
        player.setPenaltyMinutes(stats.getPenaltyMinutes());
        player.setShotPct(stats.getShotPct());
        player.setGameWinningGoals(stats.getGameWinningGoals());
        player.setOverTimeGoals(stats.getOverTimeGoals());
        player.setShortHandedGoals(stats.getShortHandedGoals());
        player.setShortHandedPoints(stats.getShortHandedPoints());
        player.setPlusMinus(stats.getPlusMinus());

        player.setLastYearGoals(pastStats.getGoals());
        player.setLastYearAssists(pastStats.getAssists());
        player.setLastYearPoints(pastStats.getPoints());
        player.setLastYearPowerPlayPoints(pastStats.getPowerPlayPoints());
        player.setLastYearShotsOnGoal(pastStats.getShots());
        player.setLastYearPlusMinus(pastStats.getPlusMinus());
        player.setLastYearBlocks(pastStats.getBlocks());
        player.setLastYearHits(pastStats.getHits());

        return player;
    }

    public AppUser addPlayerToFavorites(PlayerFavorite playerFavorite) {
        AppUser appuser = userRepository.findById(playerFavorite.getUserId()).orElseThrow(RuntimeException::new);

        if(appuser.getPlayerIds().isEmpty() && playerRepository.existsById(playerFavorite.getPlayerId())) {
            List<Player> favorites = new ArrayList<>();
            Player player = playerRepository.getOne(playerFavorite.getPlayerId());
            favorites.add(player);
            appuser.setPlayerIds(favorites);
            return userRepository.save(appuser);
        }

        else if (playerRepository.existsById(playerFavorite.getPlayerId())) {
            List<Player> favorites = appuser.getPlayerIds();
            Player player = playerRepository.getOne(playerFavorite.getPlayerId());
            favorites.add(player);
            appuser.setPlayerIds(favorites);
            return userRepository.save(appuser);

        } else if (appuser.getPlayerIds().isEmpty()) {
            List<Player> favorites = new ArrayList<>();
            Player player = new Player();
            player.setPlayerId(playerFavorite.getPlayerId());
            playerRepository.save(player);
            favorites.add(player);
            appuser.setPlayerIds(favorites);
            return userRepository.save(appuser);
        }
        else {
            List<Player> favorites = appuser.getPlayerIds();
            Player player = new Player();
            player.setPlayerId(playerFavorite.getPlayerId());
            playerRepository.save(player);
            favorites.add(player);
            appuser.setPlayerIds(favorites);
            return userRepository.save(appuser);
        }
    }

    public AppUser removePlayerFromFavorites(PlayerFavorite playerFavorite) {
        AppUser appuser = userRepository.findById(playerFavorite.getUserId()).orElseThrow(RuntimeException::new);
        List<Player> favorites = appuser.getPlayerIds();
        Player favorite = playerRepository.getOne(playerFavorite.getPlayerId());
        favorites.remove(favorite);
        playerRepository.delete(favorite);
        appuser.setPlayerIds(favorites);
        userRepository.save(appuser);
        return appuser;
    }

    public List<PlayerResponseDto> getUserPlayers(HttpServletRequest request) {
        AppUser appUser = getAppUserFromRequest(request);
        List<Player> players  = appUser.getPlayerIds();
        List<PlayerResponseDto> favPlayers = new ArrayList<>();
        for (Player player:
                players) {
            long playerId = player.getPlayerId();
            favPlayers.add(getPlayerStats(playerId, request));
        }
        return favPlayers;
    }


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
