package edu.ameier.hockey.controller;

import edu.ameier.hockey.dto.TeamFavorite;
import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.models.HockeyTeam;
import edu.ameier.hockey.services.TeamService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping
public class TeamController {

    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    public String getTeams() {
        return teamService.getTeams();
    }

    @GetMapping("/team/{id}")
    public String getTeamById(@PathVariable("id") Long id) {
        return teamService.getTeamById(id);
    }

    @GetMapping("/team/{id}/roster")
    public String getTeamRosterById(@PathVariable("id") Long id) {
        return teamService.getTeamRosterById(id);
    }

    @PostMapping("/user/team")
    public AppUser addTeam(@RequestBody TeamFavorite teamFavorite) {
        if (teamFavorite.getTeamId() == null ||  teamFavorite.getUserId() == null) {
            throw new RuntimeException("No id sent");
        }
        return teamService.addTeamToFavorites(teamFavorite);
    }

    @PatchMapping("/user/team/delete")
    public AppUser deleteTeam(@RequestBody TeamFavorite teamFavorite) {
        if (teamFavorite.getTeamId() == null ||  teamFavorite.getUserId() == null) {
            throw new RuntimeException("No id sent");
        }
        return teamService.removeTeamFromFavorites(teamFavorite);
    }

    @GetMapping("/user/id/teams")
    public List<HockeyTeam> getTeamsForUser(HttpServletRequest request)
    {
        return teamService.getUserTeams(request);
    }

    @GetMapping("/stanley")
    public String stanleyCupWinners()
    {
        return teamService.getCup();
    }

}
