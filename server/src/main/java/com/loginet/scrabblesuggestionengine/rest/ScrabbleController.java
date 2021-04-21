package com.loginet.scrabblesuggestionengine.rest;

import com.loginet.scrabblesuggestionengine.rest.to.MoveRequest;
import com.loginet.scrabblesuggestionengine.rest.to.TilesResponse;
import com.loginet.scrabblesuggestionengine.rest.to.WordRecommendationResponse;
import com.loginet.scrabblesuggestionengine.service.ScrabbleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scrabble")
public class ScrabbleController {

    private final ScrabbleService scrabbleService;

    @Autowired
    public ScrabbleController(ScrabbleService scrabbleService) {
        this.scrabbleService = scrabbleService;
    }

    @GetMapping("/pull")
    public TilesResponse getTiles() {
        return scrabbleService.getTiles();
    }

    @GetMapping("/word-recommendation/{tiles}")
    public WordRecommendationResponse recommendation(@PathVariable String[] tiles) {
        return scrabbleService.wordRecommendation(tiles);
    }

    @PostMapping("/move")
    public void move(@RequestBody MoveRequest request) {
        scrabbleService.addCharacterToTable(request);
    }

    @PutMapping("/new-game")
    public void newGame() {
        scrabbleService.newGame();
    }
}
