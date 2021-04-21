package com.loginet.scrabblesuggestionengine.rest.to;

import com.loginet.scrabblesuggestionengine.model.WordEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WordRecommendationResponse implements Serializable {

    /* best words for given tile */
    private Set<WordResponse> words = new HashSet<>();

    @Data
    @NoArgsConstructor
    public static class WordResponse {
        private Long id;
        private String value;
        private Integer score;

        public WordResponse(WordEntity entity) {
            this.id = entity.getId();
            this.value = entity.getValue();
            this.score = entity.getScore();
        }
    }
}
