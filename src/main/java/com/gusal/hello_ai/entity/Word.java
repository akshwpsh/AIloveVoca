package com.gusal.hello_ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "words")
@Getter
@Setter
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordID;

    private String word;
    private String mean;

    @OneToMany(mappedBy = "word")
    private List<WordGroupMapping> wordGroupMappings;

    // Getters and Setters
}
