package com.gusal.hello_ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_profile")
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
    private User user;

}
