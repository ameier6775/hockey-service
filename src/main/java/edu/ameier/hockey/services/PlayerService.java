package edu.ameier.hockey.services;

import org.springframework.stereotype.Service;


@Service
public class PlayerService {
    private RestTemplateService restTemplateService;

    public PlayerService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    public String getPlayerById(Long id) {
        String playerId = id.toString();
        final String url = "http://statsapi.web.nhl.com/api/v1/people/" + playerId;
        return restTemplateService.getHttpRestResponse(url);
    }
////
//    public List<Player> findAllPlayers() {
//        return playerRepository.findAll();
//    }
//
//    public Player findPlayerById(Long id) {
//        return playerRepository.findById(id).orElse(null);
//    }
//
//    public Player addNewPlayer(Player player) {
//        return playerRepository.save(player);
//    }

}
