package org.voca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voca.entity.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    boolean existsByWord(String word);
    Word findByWord(String word);
}
