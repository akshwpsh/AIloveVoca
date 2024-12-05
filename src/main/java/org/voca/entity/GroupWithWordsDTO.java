package org.voca.entity;

import java.util.List;

public class GroupWithWordsDTO {
    private Long groupId;
    private String groupName;
    private List<Word> words;

    public GroupWithWordsDTO(Group group, List<Word> words) {
        this.groupId = group.getGroupID();
        this.groupName = group.getGroupName();
        this.words = words;
    }
}
