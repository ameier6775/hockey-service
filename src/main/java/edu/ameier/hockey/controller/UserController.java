package edu.ameier.hockey.controller;

import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/user/id")
    public Map<String, Long> getUserIdForRequest(HttpServletRequest request) {
        return userService.getUserId(request);
    }

}
