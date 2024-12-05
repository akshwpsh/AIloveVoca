package org.voca.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private Group group;
}