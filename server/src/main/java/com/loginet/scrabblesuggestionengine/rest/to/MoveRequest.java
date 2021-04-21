package com.loginet.scrabblesuggestionengine.rest.to;

import com.loginet.scrabblesuggestionengine.model.Move;
import java.io.Serializable;
import lombok.Data;

@Data
public class MoveRequest implements Serializable {
    private Move move;
    private String character;

    public void validate() {
        if (move == null) {
            throw new IllegalArgumentException("Move is nut null!");
        }
        if (character == null) {
            throw new IllegalArgumentException("Character is nut null!");
        }
    }
}
