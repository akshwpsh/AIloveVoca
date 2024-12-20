package org.voca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.voca.entity.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByUserUserID(Long userID); // userID를 참조하도록 수정
    Group findByGroupName(String groupName);
    @Query("SELECT g FROM Group g JOIN g.wordGroupMappings wgm WHERE wgm.word.wordID = :wordId")
    List<Group> findGroupsByWordId(@Param("wordId") Long wordId);
}
