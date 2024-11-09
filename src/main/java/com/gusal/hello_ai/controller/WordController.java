package com.gusal.hello_ai.controller;

import com.gusal.hello_ai.entity.Group;
import com.gusal.hello_ai.entity.User;
import com.gusal.hello_ai.entity.Word;
import com.gusal.hello_ai.repository.GroupRepository;
import com.gusal.hello_ai.repository.UserRepository;
import com.gusal.hello_ai.repository.WordRepository;
import com.gusal.hello_ai.service.GroupService;
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
    private final GroupRepository groupRepository;

    GroupService groupService;
    public WordController(WordRepository wordRepository, UserRepository userRepository , GroupRepository groupRepository) {
        this.wordRepository = wordRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveWord(@RequestBody Word word) {
        if (wordRepository.existsByWord(word.getWord())) {
            return new ResponseEntity<>("이미 저장된 단어입니다.", HttpStatus.BAD_REQUEST);
        }

        Word saveWord = wordRepository.save(word);
        Group group = groupRepository.findByGroupName("저장한 단어");
        Long groupId = group.getGroupID();
        groupService.addWordToGroup(groupId, saveWord.getWordID());

        return new ResponseEntity<>(saveWord, HttpStatus.OK);
    }

    //단어와 그룹을 받아서 저장
    @PostMapping("/save/{groupId}")
    public ResponseEntity<?> saveWord(@RequestBody Word word, @PathVariable Long groupId) {
        if (wordRepository.existsByWord(word.getWord())) {
            return new ResponseEntity<>("이미 저장된 단어입니다.", HttpStatus.BAD_REQUEST);
        }
        Word saveWord = wordRepository.save(word);
        groupService.addWordToGroup(groupId, saveWord.getWordID());

        return new ResponseEntity<>(saveWord, HttpStatus.OK);
    }

//    @GetMapping("/list")
//    public ResponseEntity<?> getWords() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        logger.info(email);
//        User user  = userRepository.findByEmail(email);
//
//        List<Word> words = wordRepository.findByUser(user);
//
//        return new ResponseEntity<>(words, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteWord(@PathVariable Long id) {
//        wordRepository.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
