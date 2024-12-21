package com.gusal.hello_ai.service;

import com.gusal.hello_ai.entity.Group;
import com.gusal.hello_ai.entity.Word;
import com.gusal.hello_ai.entity.WordGroupMapping;
import com.gusal.hello_ai.repository.GroupRepository;
import com.gusal.hello_ai.repository.WordGroupMappingRepository;
import com.gusal.hello_ai.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gusal.hello_ai.entity.User;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordGroupMappingRepository wordGroupMappingRepository;

    // 그룹에 단어를 추가하는 메소드
    @Transactional
    public void addWordToGroup(Long groupId, Long wordId) {
        // 그룹과 단어를 가져옵니다.
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new RuntimeException("Word not found"));

        // WordGroupMapping 엔티티 생성
        WordGroupMapping mapping = new WordGroupMapping();
        mapping.setGroup(group);
        mapping.setWord(word);

        // 매핑 저장
        wordGroupMappingRepository.save(mapping);
    }
    public void removeWordFromGroup(Long groupId, Long wordId) {
        WordGroupMapping mapping = wordGroupMappingRepository.findByGroupGroupIDAndWordWordID(groupId, wordId);
        if (mapping != null) {
            wordGroupMappingRepository.delete(mapping);
        } else {
            throw new IllegalArgumentException("그룹에 해당 단어가 없습니다.");
        }
    }

    public void createGroup(String groupName, User user) {
        Group group = new Group();
        group.setGroupName(groupName);
        group.setUser(user);
        groupRepository.save(group);
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    public List<Group> getGroupsByUserId(Long userId) {
        return groupRepository.findByUserUserID(userId);
    }



}