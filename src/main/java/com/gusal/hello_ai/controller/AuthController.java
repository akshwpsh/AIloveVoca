package com.gusal.hello_ai.controller;

import com.gusal.hello_ai.entity.User;
import com.gusal.hello_ai.entity.UserProfile;
import com.gusal.hello_ai.service.GroupService;
import com.gusal.hello_ai.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @PostMapping("/register")
    public String signup(@RequestBody User user) {
        try {
            // userProfile이 null일 경우 초기화
            if (user.getUserProfile() == null) {
                UserProfile userProfile = new UserProfile();
                userProfile.setUser(user); // 연관 관계 설정
                user.setUserProfile(userProfile); // User에 추가
            }

            userService.saveUser(
                    user.getEmail(),
                    user.getPassword(),
                    user.getUserProfile().getName(),
                    user.getUserProfile().getScore()
            );
            groupService.createGroup("저장한 단어", user);

            return "User registered successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        try{

            return userService.loginUser(user.getEmail(), user.getPassword());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody User user){
        try{
            userService.updateUser(user.getEmail(), user.getPassword(),  user.getUserProfile().getName(), user.getUserProfile().getScore());
            return "User updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        logger.info(email);
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
