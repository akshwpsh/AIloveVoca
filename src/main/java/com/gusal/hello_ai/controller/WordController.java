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
    public ResponseEntity<?> saveWord(@RequestBody Word word, @RequestParam List<Long> groupIds) {
        Word savedWord;

        // 이미 저장된 단어인지 확인
        if (wordRepository.existsByWord(word.getWord())) {
            // 기존 단어 조회
            savedWord = wordRepository.findByWord(word.getWord());
        } else {
            // 새 단어 저장
            savedWord = wordRepository.save(word);
        }

        // 각 그룹에 단어 추가
        for (Long groupId : groupIds) {
            groupService.addWordToGroup(groupId, savedWord.getWordID());
        }

        return new ResponseEntity<>(savedWord, HttpStatus.OK);
    }

    // 단어가 포함된 그룹 목록 조회
    @GetMapping("/{wordId}/groups")
    public ResponseEntity<List<Group>> getGroupsByWordId(@PathVariable Long wordId) {
        // 특정 단어가 포함된 그룹 목록 가져오기
        List<Group> groups = groupRepository.findGroupsByWordId(wordId);

        if (groups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @PostMapping("/move")
    public ResponseEntity<?> moveWordToAnotherGroup(
            @RequestParam Long wordId,
            @RequestParam Long fromGroupId,
            @RequestParam Long toGroupId) {

        try {
            // 기존 그룹에서 단어 제거
            groupService.removeWordFromGroup(fromGroupId, wordId);

            // 새로운 그룹에 단어 추가
            groupService.addWordToGroup(toGroupId, wordId);

            return new ResponseEntity<>("단어가 그룹에서 성공적으로 이동되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("단어 이동 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
