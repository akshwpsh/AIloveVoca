package org.voca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.voca.entity.User;
import org.voca.service.GroupService;
import org.voca.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*")  // 모든 출처 허용
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @PostMapping("/register")
    public String signup(@RequestBody User user){
        logger.info("POST /register called with user: {}", user.getEmail());
        logger.info("User: {}", user);
        logger.info("User Profile: {}", user.getUserProfile().getName());
        logger.info("User Profile: {}", user.getUserProfile().getScore());
        try{
            userService.saveUser(user.getEmail(), user.getPassword(), user.getUserProfile().getName(), user.getUserProfile().getScore());
            groupService.createGroup("저장한 단어");
            return "User registered successfully";
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            return e.getMessage();
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        logger.info("POST /login called with user: {}", user.getEmail());
        try{
            return userService.loginUser(user.getEmail(), user.getPassword());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody User user){
        logger.info("POST /update called with user: {}", user.getEmail());
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
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}