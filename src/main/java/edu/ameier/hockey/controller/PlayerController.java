package edu.ameier.hockey.controller;

import edu.ameier.hockey.dto.PlayerFavorite;
import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.models.Player;
import edu.ameier.hockey.services.PlayerService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/team/player/{id}")
    public String getPlayerById(@PathVariable("id") Long id) {return playerService.getPlayerById(id); }

    @GetMapping("/user/player/{id}/stats")
    public String getPlayerStats(@PathVariable("id") Long id) {return playerService.getPlayerStats(id); }

    @PostMapping("/user/player")
    public AppUser addPlayer(@RequestBody PlayerFavorite playerFavorite) {
        if (playerFavorite.getPlayerId() == null ||  playerFavorite.getUserId() == null) {
            throw new RuntimeException("No id sent");
        }
        return playerService.addPlayerToFavorites(playerFavorite);
    }

    @PatchMapping("/user/player/delete")
    public AppUser deletePlayer(@RequestBody PlayerFavorite playerFavorite) {
        if (playerFavorite.getPlayerId() == null ||  playerFavorite.getUserId() == null) {
            throw new RuntimeException("No id sent");
        }
        return playerService.removePlayerFromFavorites(playerFavorite);
    }

    @GetMapping("/user/id/players")
    public List<Player> getPlayersForUser(HttpServletRequest request)
    {
        return playerService.getUserPlayers(request);
    }



}
