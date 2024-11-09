package com.gusal.hello_ai.service;

import com.gusal.hello_ai.controller.AuthController;
import com.gusal.hello_ai.entity.User;
import com.gusal.hello_ai.jwt.JwtUtil;
import com.gusal.hello_ai.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
    public void saveUser(String email, String password, String username) throws Exception {
        if(userRepository.findByEmail(email) != null)
        {
            throw new Exception("User already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public String loginUser(String email, String password)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        if(authentication.isAuthenticated())
        {
            return jwtUtil.CreateToken(email);
        }
        return "Invalid username or password";
    }

    public void updateUser(String email, String password, String username) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null)
        {
            throw new Exception("User does not exist");
        }
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User getUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        return user;
    }

//    public User getUserBySecurity(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        logger.info("name: " + authentication.getName());
//        User user =  userRepository.findByEmail(authentication.getName());
//        return user;
//    }


}
