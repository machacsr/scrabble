package com.loginet.scrabblesuggestionengine.repository;

import com.loginet.scrabblesuggestionengine.model.WordEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository {

    List<WordEntity> findAll();
    Optional<WordEntity> findById(Long id);
}
