package org.voca.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voca.entity.Group;
import org.voca.entity.Word;
import org.voca.repository.GroupRepository;
import org.voca.repository.UserRepository;
import org.voca.repository.WordRepository;
import org.voca.service.GroupService;

@RestController
@RequestMapping("/api/words")
@CrossOrigin(origins = "*")  // 모든 출처 허용
public class WordController {


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
}
