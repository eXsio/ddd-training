package com.ddd.poc.command.security.command;

import com.ddd.poc.domain.core.command.DomainCommand;

public class UpdateGroupCommand implements DomainCommand {

    private final Long groupId;

    private final String newName;

    public UpdateGroupCommand(Long groupId, String newName) {
        this.groupId = groupId;
        this.newName = newName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getNewName() {
        return newName;
    }
}
