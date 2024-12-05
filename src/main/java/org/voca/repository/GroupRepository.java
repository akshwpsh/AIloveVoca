package org.voca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voca.entity.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByUserUserID(Long userID); // userID를 참조하도록 수정
    Group findByGroupName(String groupName);
}
