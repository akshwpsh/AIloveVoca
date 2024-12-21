package com.gusal.hello_ai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "UserGroup")
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupID;

    private String groupName;

    @ManyToOne
    @JoinColumn(name = "userID")
    @JsonIgnore // 순환 참조 방지
    private User user;

    @OneToMany(mappedBy = "group")
    private List<WordGroupMapping> wordGroupMappings;
}
