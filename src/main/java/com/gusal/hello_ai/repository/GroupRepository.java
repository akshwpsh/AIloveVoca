package com.gusal.hello_ai.repository;

import com.gusal.hello_ai.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByUserId(Long userId);
    Group findByGroupName(String groupName);
}
