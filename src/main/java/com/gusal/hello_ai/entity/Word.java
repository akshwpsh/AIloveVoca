package com.gusal.hello_ai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Word")
@Getter
@Setter
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordID;

    private String word;
    private String mean;

    @OneToMany(mappedBy = "word")
    @JsonIgnore // 순환 참조 방지
    private List<WordGroupMapping> wordGroupMappings;

    // Getters and Setters
}
