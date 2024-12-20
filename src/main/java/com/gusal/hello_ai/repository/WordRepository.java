package com.gusal.hello_ai.repository;

import com.gusal.hello_ai.entity.User;
import com.gusal.hello_ai.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    boolean existsByWord(String word);
    Word findByWord(String word);
}
