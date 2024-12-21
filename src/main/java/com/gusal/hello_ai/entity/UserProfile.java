package com.gusal.hello_ai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "UserProfile")
@Getter
@Setter
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileID;

    private String name;
    private Long score;

    @OneToOne
    @JoinColumn(name = "userID")
    @JsonIgnore // 순환 참조 방지
    private User user;

}
