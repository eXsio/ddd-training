package com.ddd.poc.command.security.command;

import com.ddd.poc.domain.core.command.DomainCommand;

public class LeaveGroupCommand extends DomainCommand {

    private final Long groupId;

    private final Long userId;

    public LeaveGroupCommand(Long groupId, Long userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Long getUserId() {
        return userId;
    }
}
