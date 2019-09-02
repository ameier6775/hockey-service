package edu.ameier.hockey.controller;

import edu.ameier.hockey.dto.TeamFavorite;
import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.models.HockeyTeam;
import edu.ameier.hockey.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public AppUser addUser(@RequestBody AppUser appUser) {
        return userService.addUser(appUser);
    }

    @PostMapping("/user/team")
    public AppUser addTeam(@RequestBody TeamFavorite teamFavorite) {
        if (teamFavorite.getTeamId() == null ||  teamFavorite.getUserId() == null) {
            throw new RuntimeException("No id sent");
        }
        return userService.addTeamToFavorites(teamFavorite);
    }

    @PatchMapping("/user/team/delete")
    public AppUser deleteTeam(@RequestBody TeamFavorite teamFavorite) {
        if (teamFavorite.getTeamId() == null ||  teamFavorite.getUserId() == null) {
            throw new RuntimeException("No id sent");
        }
        return userService.removeTeamFromFavorites(teamFavorite);
    }

    @GetMapping("/user/id")
    public Map<String, Long> getUserIdForRequest(HttpServletRequest request)
    {
        return userService.getUserId(request);
    }

    @GetMapping("/user/id/teams")
    public List<HockeyTeam> getTeamsForUser(HttpServletRequest request)
    {
        return userService.getUserTeams(request);
    }

}
    //    @PostMapping("/login")
//    public AppUser checkUser(String username, String password) {
//
//        Boolean loggedIn = userService.checkUser(username, password);
//        if (loggedIn) {
//            userRepository.
//        }
//    }
