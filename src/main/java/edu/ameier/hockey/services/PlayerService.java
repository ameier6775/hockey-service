package edu.ameier.hockey.services;

import edu.ameier.hockey.dto.PlayerFavorite;
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
import java.util.ArrayList;
import java.util.List;

import static edu.ameier.hockey.security.SecurityConstants.*;

@Slf4j
@Service
public class PlayerService {
    private RestTemplateService restTemplateService;
    private UserRepository userRepository;
    private PlayerRepository playerRepository;

    public PlayerService(RestTemplateService restTemplateService, UserRepository userRepository, PlayerRepository playerRepository) {
        this.restTemplateService = restTemplateService;
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
    }

    public String getPlayerById(Long id) {
        String playerId = id.toString();
        final String url = "http://statsapi.web.nhl.com/api/v1/people/" + playerId;
        return restTemplateService.getHttpRestResponse(url);
    }

    public String getPlayerStats(Long id) {
        String playerId = id.toString();
        final String url = "http://statsapi.web.nhl.com/api/v1/people/" + playerId + "/stats?stats=statsSingleSeason&season=20182019";
        return restTemplateService.getHttpRestResponse(url);
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
    public List<Player> getUserPlayers(HttpServletRequest request) {
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
        return appUser.getPlayerIds();
    }
}
