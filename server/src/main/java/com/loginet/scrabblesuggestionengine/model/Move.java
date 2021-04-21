package com.loginet.scrabblesuggestionengine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Move {
    private int rowIndex;
    private char columnIndex;

    public Move(int rowIndex, char columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }
}
