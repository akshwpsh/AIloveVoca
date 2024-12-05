package org.voca.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.voca.entity.Group;
import org.voca.entity.GroupWithWordsDTO;
import org.voca.entity.User;
import org.voca.service.GroupService;
import org.voca.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")  // 모든 출처 허용
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    // 그룹에 단어 추가하는 API
    @PostMapping("/{groupId}/words/{wordId}")
    public void addWordToGroup(@PathVariable Long groupId, @PathVariable Long wordId) {
        groupService.addWordToGroup(groupId, wordId);
    }


    @PostMapping("/create")
    public void createGroup(@RequestBody String groupName) {
        groupService.createGroup(groupName);
    }


    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
    }

    @GetMapping("/list")
    public List<Group> getGroupsByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        return groupService.getGroupsByUserId(user.getUserID());
    }

    @GetMapping("/words")
    public List<GroupWithWordsDTO> getGroupsWithWords() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        return userService.getAllGroupsWithWordsByUserId(user.getUserID());
    }
}
