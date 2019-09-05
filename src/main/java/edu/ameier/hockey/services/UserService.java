package edu.ameier.hockey.services;

import edu.ameier.hockey.dto.TeamFavorite;
import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.models.HockeyTeam;
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

    public UserService(UserRepository userRepository, TeamRepository teamRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public AppUser addUser(AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    public AppUser addTeamToFavorites(TeamFavorite teamFavorite) {
        AppUser appuser = userRepository.findById(teamFavorite.getUserId()).orElseThrow(RuntimeException::new);

//        if (appuser.getTeamIds().contains(hockeyTeam)) {
//
//        }
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

    public List<HockeyTeam> getUserTeams(HttpServletRequest request)
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

        List<HockeyTeam> response = appUser.getTeamIds();

        return response;
    }

//    public Boolean checkUser(String username, String password) {
//        List<AppUser> appUsers = userRepository.findAll();
//        for (AppUser appUser :
//                appUsers) {
//            String name = appUser.getUserName();
//            String secret = appUser.getPassword();
//            if (name.equals(username) && secret.equals(password)) {
//                return true;
//            }
//        }
//        return false;
//    }
    }
