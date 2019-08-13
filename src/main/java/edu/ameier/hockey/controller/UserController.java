package edu.ameier.hockey.controller;

import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.services.UserService;
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
