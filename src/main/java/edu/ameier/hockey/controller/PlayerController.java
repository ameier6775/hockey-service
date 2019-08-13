package edu.ameier.hockey.controller;

import edu.ameier.hockey.services.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/team/player/{id}")
    public String getPlayerById(@PathVariable("id") Long id) {return playerService.getPlayerById(id); }

}
