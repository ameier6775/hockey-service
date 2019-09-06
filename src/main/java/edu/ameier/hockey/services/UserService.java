package edu.ameier.hockey.services;

import edu.ameier.hockey.dto.PlayerFavorite;
import edu.ameier.hockey.dto.TeamFavorite;
import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.models.HockeyTeam;
import edu.ameier.hockey.models.Player;
import edu.ameier.hockey.repositories.PlayerRepository;
import edu.ameier.hockey.repositories.TeamRepository;
import edu.ameier.hockey.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.ameier.hockey.security.SecurityConstants.*;

@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;

    public UserService(UserRepository userRepository, TeamRepository teamRepository, PlayerRepository playerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public AppUser addUser(AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
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

    public Map<String, Long> getUserId(HttpServletRequest request)
    {
        String token = request.getHeader(HEADER_STRING);

        if (token == null)
        {
            throw new RuntimeException("token is null");
        }
            SecretKey key = new SecretKeySpec(SECRET.getBytes(), "HmacSHA512");
            // parse the token.
            String userName = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            AppUser appUser = userRepository.findByUserName(userName);

        Map<String, Long> response = new HashMap<>();

        response.put("userId", appUser.getId());
        return response;
    }


    }
