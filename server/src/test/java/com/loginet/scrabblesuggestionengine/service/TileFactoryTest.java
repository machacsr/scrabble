package com.loginet.scrabblesuggestionengine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TileFactoryTest {

    @Autowired
    private TileFactory tileFactory;

    @BeforeEach
    public void injectNonNull() {
        assertNotNull(tileFactory);
    }

    @Test
    public void countTest() {
        final long countOfTiles = tileFactory.getTilesByScore()
                .entrySet()
                .parallelStream()
                .map(Entry::getValue)
                .mapToInt(List::size)
                .sum();
        assertEquals(100, countOfTiles);
    }
}
