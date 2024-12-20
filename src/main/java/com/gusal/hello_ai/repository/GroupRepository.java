package com.gusal.hello_ai.repository;

import com.gusal.hello_ai.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByUserUserID(Long userID); // userID를 참조하도록 수정
    Group findByGroupName(String groupName);
    @Query("SELECT g FROM Group g JOIN g.wordGroupMappings wgm WHERE wgm.word.wordID = :wordId")
    List<Group> findGroupsByWordId(@Param("wordId") Long wordId);
}
