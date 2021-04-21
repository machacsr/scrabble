package com.loginet.scrabblesuggestionengine.rest.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TilesResponse implements Serializable {

    /* best words for given tile */
    private List<String> tiles = new ArrayList<>();

}
