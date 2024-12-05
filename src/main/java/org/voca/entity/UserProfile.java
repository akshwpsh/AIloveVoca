package org.voca.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private User user;
}