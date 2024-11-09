package com.gusal.hello_ai.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "word_gorup_mapping")
@Getter
@Setter
public class WordGroupMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long word_group_mappingID;

    @ManyToOne
    @JoinColumn(name = "wordID")
    private Word word;

    @ManyToOne
    @JoinColumn(name = "groupID")
    private Group group;

}
