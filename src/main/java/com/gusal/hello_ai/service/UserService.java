package com.gusal.hello_ai.service;

import com.gusal.hello_ai.controller.AuthController;
import com.gusal.hello_ai.entity.Group;
import com.gusal.hello_ai.entity.GroupWithWordsDTO;
import com.gusal.hello_ai.entity.User;
import com.gusal.hello_ai.jwt.JwtUtil;
import com.gusal.hello_ai.repository.GroupRepository;
import com.gusal.hello_ai.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
    public void saveUser(String email, String password, String username, Long score) throws Exception {
        if(userRepository.findByEmail(email) != null)
        {
            throw new Exception("User already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.getUserProfile().setName(username);
        user.getUserProfile().setScore(score);
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

    public void updateUser(String email, String password, String username, Long score) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null)
        {
            throw new Exception("User does not exist");
        }
        user.getUserProfile().setName(username);
        user.getUserProfile().setScore(score);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User getUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        return user;
    }

    // 유저의 모든 단어 그룹을 반환하는 메소드
    public List<Group> getAllGroupsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return groupRepository.findByUserUserID(user.getUserID());
    }

    // 유저의 모든 그룹과 각 그룹에 포함된 단어를 반환하는 메소드
    public List<GroupWithWordsDTO> getAllGroupsWithWordsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Group> groups = groupRepository.findByUserUserID(user.getUserID());

        // 각 그룹에 포함된 단어를 DTO로 변환하여 반환
        return groups.stream()
                .map(group -> new GroupWithWordsDTO(group, group.getWordGroupMappings().stream()
                        .map(mapping -> mapping.getWord())
                        .collect(Collectors.toList()))
                )
                .collect(Collectors.toList());
    }

//    public User getUserBySecurity(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        logger.info("name: " + authentication.getName());
//        User user =  userRepository.findByEmail(authentication.getName());
//        return user;
//    }


}
