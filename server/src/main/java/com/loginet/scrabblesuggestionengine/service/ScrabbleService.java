package com.loginet.scrabblesuggestionengine.service;

import com.loginet.scrabblesuggestionengine.model.Move;
import com.loginet.scrabblesuggestionengine.model.TableEntity;
import com.loginet.scrabblesuggestionengine.model.WordEntity;
import com.loginet.scrabblesuggestionengine.repository.WordRepository;
import com.loginet.scrabblesuggestionengine.rest.to.MoveRequest;
import com.loginet.scrabblesuggestionengine.rest.to.TilesResponse;
import com.loginet.scrabblesuggestionengine.rest.to.WordRecommendationResponse;
import com.loginet.scrabblesuggestionengine.rest.to.WordRecommendationResponse.WordResponse;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ScrabbleService {

    private final TileFactory tileFactory;
    private final WordRepository wordRepository;
    private TableEntity tableEntity = new TableEntity();

    @Autowired
    public ScrabbleService(TileFactory tileFactory, WordRepository wordRepository) {
        this.tileFactory = tileFactory;
        this.wordRepository = wordRepository;
    }

    /**
     * Validate and add character to give move
     */
    public void addCharacterToTable(MoveRequest request) {
        request.validate();

        if (tileFactory.getScoreByTile().get(request.getCharacter().toUpperCase(Locale.ROOT)) == null) {
            throw new IllegalArgumentException("Not allowed character!");
        }

        tableEntity.addCharacter(request.getMove(), request.getCharacter());
    }

    /**
     * Recommend best five option to give tiles
     */
    public WordRecommendationResponse wordRecommendation(String[] tiles) {
        final WordRecommendationResponse response = new WordRecommendationResponse();

        /* group by word length */
        final Map<Integer, List<WordEntity>> wordsByLength = wordRepository
                .findAll()
                .stream()
                .filter(it -> {
                    String word = it.getValue().toUpperCase(Locale.ROOT);

                    /* iterate by the given tiles */
                    for (String tile : tiles) {
                        if (word.contains(tile.toUpperCase(Locale.ROOT))) {
                            /* cut from original string */
                            word = new StringBuilder(word)
                                    .delete(word.indexOf(tile), word.indexOf(tile) + tile.length())
                                    .toString();
                        }
                    }

                    /* if original string has any character then we dont have to puzzle */
                    return word.isEmpty() || word.isBlank();
                })
                .collect(Collectors.groupingBy(it -> it.getValue().length(), HashMap::new, Collectors.toList()));

        /* my tiles */
        for (String tile : tiles) {
            /* find first move for tile */
            final Optional<Move> moveByTile = tableEntity.findMoveByTile(tile.toUpperCase(Locale.ROOT));

            if (moveByTile.isPresent()) {
                final int maximumSpaceLength = tableEntity.getMaximumSpaceLength(moveByTile.get());

                final List<Integer> iterationCollection = IntStream.rangeClosed(2, maximumSpaceLength).boxed()
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.toList());

                /* start form biggest score */
                for (Integer scoreNumber : iterationCollection) {
                    if (wordsByLength.get(scoreNumber) == null) {
                        continue;
                    }

                    final List<WordEntity> sortedByScore = wordsByLength
                            .get(scoreNumber)
                            .parallelStream()
                            .sorted(Comparator.comparing(WordEntity::getScore).reversed())
                            .collect(Collectors.toList());

                    /* filter words */
                    final List<WordEntity> filteredWords = sortedByScore.stream()
                            .filter(it -> tableEntity.isGoodForRow(it.getValue(), moveByTile.get().getRowIndex())
                                    || tableEntity
                                    .isGoodForColumn(it.getValue(), tableEntity.indexOfChar(moveByTile.get().getColumnIndex())))
                            .collect(Collectors.toList());

                    /* if find word by score then add five option */
                    if (!filteredWords.isEmpty() && response.getWords().size() <= 5) {
                        IntStream.range(0, Math.min(filteredWords.size(), 5))
                                .forEach(idx -> {
                                    response.getWords().add(new WordResponse(filteredWords.get(idx)));
                                });
                    }
                }
            }
        }

        /* limit response to best five option */
        response.setWords(response.getWords()
                .parallelStream()
                .sorted(Comparator.comparing(WordResponse::getScore).reversed())
                .limit(5)
                .collect(Collectors.toSet()));

        return response;
    }

    public TilesResponse getTiles() {
        final TilesResponse response = new TilesResponse();
        final Random random = new Random();

        final List<String> tiles = tileFactory.getTilesByScore()
                .entrySet()
                .parallelStream()
                .map(Entry::getValue)
                .flatMap(List::stream)
                .map(Serializable::toString)
                .collect(Collectors.toList());

        do {
            final int randomIndex = random.nextInt(tiles.size() + 1);
            final String tile = tiles.get(randomIndex);
            response.getTiles().add(tile);
            tiles.remove(randomIndex);
        } while (response.getTiles().size() < 7);

        return response;
    }

    public void newGame() {
        this.tableEntity = new TableEntity();
    }
}
