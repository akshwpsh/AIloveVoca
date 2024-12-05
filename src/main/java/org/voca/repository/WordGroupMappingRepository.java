package org.voca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voca.entity.WordGroupMapping;

@Repository
public interface WordGroupMappingRepository extends JpaRepository<WordGroupMapping, Long> {
}
