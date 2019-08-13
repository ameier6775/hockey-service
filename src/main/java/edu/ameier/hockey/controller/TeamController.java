package edu.ameier.hockey.controller;

import edu.ameier.hockey.services.HockeyTeamService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TeamController {

    private HockeyTeamService hockeyTeamService;

    public TeamController(HockeyTeamService hockeyTeamService) {
        this.hockeyTeamService = hockeyTeamService;
    }

    @GetMapping("/team")
    public String getTeams() {
        return hockeyTeamService.getTeams();
    }

    @GetMapping("/team/{id}")
    public String getTeamById(@PathVariable("id") Long id) {
        return hockeyTeamService.getTeamById(id);
    }

    @GetMapping("/team/{id}/roster")
    public String getTeamRosterById(@PathVariable("id") Long id) {
        return hockeyTeamService.getTeamRosterById(id);
    }

    @GetMapping("/stanley")
    public String stanleyCupWinners()
    {
        return hockeyTeamService.getCup();
    }
}
