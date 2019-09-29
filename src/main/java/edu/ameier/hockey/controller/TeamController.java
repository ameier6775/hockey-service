package edu.ameier.hockey.controller;

import edu.ameier.hockey.dto.nhlTeam.UserTeamDto;
import edu.ameier.hockey.dto.nhlTeam.TeamFavorite;
import edu.ameier.hockey.dto.nhlTeam.UserTeamsDto;
import edu.ameier.hockey.models.AppUser;
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
    public List<UserTeamsDto> getTeams(HttpServletRequest request) {
        return teamService.getTeams(request);
    }

    @GetMapping("/team/{id}")
    public UserTeamDto getTeamById(@PathVariable("id") Long id, HttpServletRequest request) {
        return teamService.getTeamById(id, request);
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
    public List<UserTeamDto> getTeamsForUser(HttpServletRequest request)
    {
        return teamService.getUserTeams(request);
    }

    @GetMapping("/stanley")
    public String stanleyCupWinners()
    {
        return teamService.getCup();
    }

    @GetMapping("/standings")
    public String getNHLStandings() { return teamService.getNHLStandings(); }

}
