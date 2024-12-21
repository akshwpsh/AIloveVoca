package com.gusal.hello_ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TBL_USER")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String loginID;
    private String password;
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user")
    private List<Group> groups;

    // 기본 생성자
    public User() {
        this.userProfile = new UserProfile(); // 기본 초기화
        this.userProfile.setUser(this); // 연관 관계 설정
    }

}
