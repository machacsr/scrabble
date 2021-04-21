package com.loginet.scrabblesuggestionengine;

import com.loginet.scrabblesuggestionengine.model.WordEntity;
import com.loginet.scrabblesuggestionengine.repository.WordRepository;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@ServletComponentScan
@SpringBootApplication
public class ScrabbleSuggestionEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrabbleSuggestionEngineApplication.class, args);
    }
}
