package com.loginet.scrabblesuggestionengine.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TileFactory {

    private Map<Integer, List<Serializable>> tilesByScore = new HashMap<>();
    private Map<String, Integer> scoreByTile = new HashMap<>();

    public TileFactory() {
        this.tilesByScore.put(0, Arrays.asList(
                "JOKER", "JOKER"
        ));
        this.tilesByScore.put(1, Arrays.asList(
                'A', 'A', 'A', 'A', 'A', 'A', // 6
                'E', 'E', 'E', 'E', 'E', 'E',
                'K', 'K', 'K', 'K', 'K', 'K',
                'T', 'T', 'T', 'T', 'T', // 5
                'Á', 'Á', 'Á', 'Á', // 4
                'L', 'L', 'L', 'L',
                'N', 'N', 'N', 'N',
                'R', 'R', 'R', 'R',
                'I', 'I', 'I',  // 3
                'M', 'M', 'M',
                'O', 'O', 'O',
                'S', 'S', 'S'
        ));
        this.tilesByScore.put(2, Arrays.asList(
                'B', 'B', 'B',  // 3
                'D', 'D', 'D',
                'G', 'G', 'G',
                'Ó', 'Ó', 'Ó'
        ));
        this.tilesByScore.put(3, Arrays.asList(
                'É', 'É', 'É',  // 3
                'H', 'H',       // 2
                "SZ", "SZ",
                'V', 'V'
        ));
        this.tilesByScore.put(4, Arrays.asList(
                'F', 'F',       // 2
                "GY", "GY",
                'J', 'J',
                'Ö', 'Ö',
                'P', 'P',
                'U', 'U',
                'Ü', 'Ü',
                'Z', 'Z'
        ));
        this.tilesByScore.put(5, Arrays.asList(
                'C',            // 1
                'Í',
                "NY"
        ));
        this.tilesByScore.put(7, Arrays.asList(
                "CS",            // 1
                'Ő',
                'Ú',
                'Ű'
        ));
        this.tilesByScore.put(8, Arrays.asList(
                "LY",            // 1
                "ZS"
        ));
        this.tilesByScore.put(10, Collections.singletonList(
                "TY"             // 1
        ));

        tilesByScore.forEach((key, value) -> {
            for (Serializable character : new HashSet<>(value)) {
                scoreByTile.put(character.toString(), key);
            }
        });
    }

    public Map<Integer, List<Serializable>> getTilesByScore() {
        return tilesByScore;
    }

    public Map<String, Integer> getScoreByTile() {
        return scoreByTile;
    }
}
