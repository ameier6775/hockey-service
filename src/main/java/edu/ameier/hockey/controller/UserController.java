package edu.ameier.hockey.controller;

import edu.ameier.hockey.dto.TeamFavorite;
import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user/team/favorite")
    @ResponseStatus(HttpStatus.OK)
    public AppUser addTeam(@RequestBody TeamFavorite teamFavorite) {
        if (teamFavorite.getTeamId() == null ||  teamFavorite.getUserId() == null) {
            throw new RuntimeException("No id sent");
        }
        return userService.addTeamToFavorites(teamFavorite);
    }
//    @GetMapping
//    @ResponseBody
//    public AppUser addUser(@RequestParam)
}
    //    @PostMapping("/login")
//    public AppUser checkUser(String username, String password) {
//
//        Boolean loggedIn = userService.checkUser(username, password);
//        if (loggedIn) {
//            userRepository.
//        }
//    }
