package com.gusal.hello_ai.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "WordGroupMapping")
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
    @JsonIgnore // 순환 참조 방지
    private Group group;

}
