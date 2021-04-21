package com.loginet.scrabblesuggestionengine.model;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Data;

@Data
public class TableEntity {

    private char[] alphabet = new char[15];
    private String[][] cells = new String[15][15];

    public TableEntity() {
        char character;
        int i = 0;
        for (character = 'A'; character <= 'O'; ++character) {
            alphabet[i] = character;
            i++;
        }
    }

    public Optional<Move> findMoveByTile(String tile) {
        int columnIndex = 0;
        int rowIndex = 0;

        for (String[] row : cells) {
            for (String cell : row) {
                if (cell != null && cell.equals(tile)) {
                    return Optional.of(new Move(rowIndex, alphabet[columnIndex]));
                }
                columnIndex++;
            }
            columnIndex = 0;
            rowIndex++;
        }

        return Optional.empty();
    }

    public boolean isGoodForRow(String word, int rowIndex) {
        word = word.toUpperCase(Locale.ROOT);
        for (int columIndex = 0; columIndex < 15; columIndex++) {
            if (cells[rowIndex][columIndex] != null && word.contains(cells[rowIndex][columIndex])) {
                final int overlapIndex = word.indexOf(cells[rowIndex][columIndex]);

                /* check has enough space */
                if (columIndex <= overlapIndex) {
                    return false;
                }

                /* if has enough space then check space is empty */
                for (int i = 0; i < overlapIndex; i++) {
                    if (cells[rowIndex][i] != null) {
                        return false;
                    }
                }

                /* if has enough space from another side */
                for (int i = word.length() - overlapIndex; i < 15; i++) {
                    if (cells[rowIndex][i] != null) {
                        return false;
                    }
                }

                return true;
            }
        }
        return false;
    }

    public boolean isGoodForColumn(String word, int columIndex) {
        word = word.toUpperCase(Locale.ROOT);
        for (int rowIndex = 0; rowIndex < 15; rowIndex++) {
            if (cells[rowIndex][columIndex] != null && word.contains(cells[rowIndex][columIndex])) {
                final int overlapIndex = word.indexOf(cells[rowIndex][columIndex]);

                /* check has enough space */
                if (rowIndex <= overlapIndex) {
                    return false;
                }

                /* if has enough space then check space is empty */
                for (int i = 0; i < overlapIndex; i++) {
                    if (cells[i][columIndex] != null) {
                        return false;
                    }
                }

                /* if has enough space from another side */
                for (int i = word.length() - overlapIndex; i < 15; i++) {
                    if (cells[i][columIndex] != null) {
                        return false;
                    }
                }

                return true;
            }
        }
        return false;
    }

    public int getMaximumSpaceLength(Move move) {
        int max = 0;
        int biggest = 0;
        for (int i = 0; i < 15; i++) {
            if (cells[i][indexOfChar(move.getColumnIndex())] == null) {
                max++;
            } else {
                if (biggest < max) {
                    biggest = max;
                }
                max = 0;
            }
        }

        return Math.max(max, biggest);
    }

    public void addCharacter(Move move, String character) {
        isValidMove(move);

        cells[move.getRowIndex()][indexOfChar(move.getColumnIndex())] = character;
    }

    private void isValidMove(Move move) {
        isBetweenHorizontalRange(move.getColumnIndex());
        isBetweenVerticalRange(move.getRowIndex());

        if (cells[move.getRowIndex()][indexOfChar(move.getColumnIndex())] != null) {
            throw new IllegalArgumentException("Given cell is not empty!");
        }
    }

    private void isBetweenHorizontalRange(char HorizontalMove) {
        /* check Horizontal move is allowed */
        boolean moveIsBetweenRange = false;
        for (char c : alphabet) {
            if (c == HorizontalMove) {
                moveIsBetweenRange = true;
                break;
            }
        }
        if (!moveIsBetweenRange) {
            throw new IllegalArgumentException("Not allowed columnIndex move!");
        }
    }

    private void isBetweenVerticalRange(int verticalMove) {
        /* check Horizontal move is allowed */
        final boolean moveIsBetweenRange = IntStream.rangeClosed(0, 15)
                .boxed()
                .collect(Collectors.toList())
                .contains(verticalMove);

        if (!moveIsBetweenRange) {
            throw new IllegalArgumentException("Not allowed rowIndex move!");
        }
    }

    public int indexOfChar(char character) {
        /* check Horizontal move is allowed */
        int index = 0;
        for (char c : alphabet) {
            if (c == character) {
               return index;
            }
            index++;
        }
        throw new IllegalArgumentException("Character not found in table!");
    }

}
