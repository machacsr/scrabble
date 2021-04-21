package com.loginet.scrabblesuggestionengine.repository.impl;

import com.loginet.scrabblesuggestionengine.model.WordEntity;
import com.loginet.scrabblesuggestionengine.repository.WordRepository;
import com.loginet.scrabblesuggestionengine.service.TileFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WordRepositoryImpl implements WordRepository {

    private final TileFactory tileFactory;
    private final List<WordEntity> localDatabase = new ArrayList<>();
    private final List<String> hungarianAlphabet = Arrays
            .asList("CS", "GY", "LY", "NY", "SZ", "TY", "ZS");

    @Autowired
    public WordRepositoryImpl(TileFactory tileFactory) {
        this.tileFactory = tileFactory;
        initLocalDatabase();
    }

    @Override
    public List<WordEntity> findAll() {
        return localDatabase;
    }

    @Override
    public Optional<WordEntity> findById(Long id) {
        return localDatabase.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    private void initLocalDatabase() {
        //not working with ISO-8859-1
        try (BufferedReader br = Files
                .newBufferedReader(Paths.get(ClassLoader.getSystemResource("input/freedict").toURI()), Charset.forName("ISO-8859-2"))) {
            String line;
            long id = 0L;
            while ((line = br.readLine()) != null) {
                /* if contains not allowed keyword then jump it */
                if (line.contains("-") || line.contains("'") || line.length() <= 1 || line.length() >= 15) {
                    continue;
                }
                Integer wordScore = calculateWordScore(line);
                final WordEntity wordEntity = new WordEntity(id, line, wordScore);
                localDatabase.add(wordEntity);
                id += 1;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Integer calculateWordScore(String word) {
        Integer sum = 0;

        word = word.toUpperCase(Locale.ROOT);

        /* first if all find hungarian special chars */
        while (hungarianAlphabet.stream().anyMatch(word::contains)) {
            final String specialChar = getPossibleSpecialCharacterFromWord(word);
            sum += tileFactory.getScoreByTile().getOrDefault(specialChar, 0);

            /* cut from original string */
            word = new StringBuilder(word)
                    .delete(word.indexOf(specialChar), word.indexOf(specialChar) + specialChar.length())
                    .toString();
        }

        /* iterate normal characters */
        for (String s : word.split("")) {
            sum += tileFactory.getScoreByTile().getOrDefault(s, 0);
        }
        return sum;
    }

    private String getPossibleSpecialCharacterFromWord(String word) {
        return hungarianAlphabet.stream().filter(word::contains).findFirst().get();
    }
}
