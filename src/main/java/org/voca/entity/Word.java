package org.voca.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
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
    private List<WordGroupMapping> wordGroupMappings;
}