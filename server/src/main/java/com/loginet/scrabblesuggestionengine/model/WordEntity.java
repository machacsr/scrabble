package com.loginet.scrabblesuggestionengine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WordEntity {

    private Long id;
    private String value;
    private Integer score;

    public WordEntity(Long id, String value, Integer score) {
        this.id = id;
        this.value = value;
        this.score = score;
    }
}
