package com.ddd.poc.command.security.command;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.security.dto.UserDTO;

public class DeleteGroupCommand implements DomainCommand {

    private final Long groupId;

    public DeleteGroupCommand(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }
}
