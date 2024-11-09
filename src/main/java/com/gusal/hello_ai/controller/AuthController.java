package com.gusal.hello_ai.controller;

import com.gusal.hello_ai.entity.User;
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

    @PostMapping("/register")
    public String signup(@RequestBody User user){
        try{
            userService.saveUser(user.getEmail(), user.getPassword(), user.getUsername());
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
            userService.updateUser(user.getEmail(), user.getPassword(), user.getUsername());
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
