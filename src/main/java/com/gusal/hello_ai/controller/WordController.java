package com.gusal.hello_ai.controller;

import com.gusal.hello_ai.entity.User;
import com.gusal.hello_ai.entity.Word;
import com.gusal.hello_ai.repository.UserRepository;
import com.gusal.hello_ai.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
public class WordController {
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final WordRepository wordRepository;
    private final UserRepository userRepository;

    public WordController(WordRepository wordRepository, UserRepository userRepository) {
        this.wordRepository = wordRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveWord(@RequestBody Word word) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        logger.info(email);
        User user  = userRepository.findByEmail(email);

        if (wordRepository.existsByUserAndWord(user, word.getWord())) {
            return new ResponseEntity<>("이미 저장된 단어입니다.", HttpStatus.BAD_REQUEST);
        }

        word.setUser(user);
        Word saveWord = wordRepository.save(word);

        return new ResponseEntity<>(saveWord, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getWords() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        logger.info(email);
        User user  = userRepository.findByEmail(email);

        List<Word> words = wordRepository.findByUser(user);

        return new ResponseEntity<>(words, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWord(@PathVariable Long id) {
        wordRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
