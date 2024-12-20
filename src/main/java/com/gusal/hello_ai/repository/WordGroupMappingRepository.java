package com.gusal.hello_ai.repository;

import com.gusal.hello_ai.entity.WordGroupMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordGroupMappingRepository extends JpaRepository<WordGroupMapping, Long> {
    WordGroupMapping findByGroupGroupIDAndWordWordID(Long groupId, Long wordId);
}
