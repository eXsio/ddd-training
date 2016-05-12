package com.ddd.poc.domain.security.event;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;

public class GroupUpdatedEvent implements DomainEvent {

    private final Long groupId;

    private final String newName;

    public GroupUpdatedEvent(Long groupId, String newName) {
        this.groupId = groupId;
        this.newName = newName;
    }


    public String getNewName() {
        return newName;
    }

    public Long getGroupId() {
        return groupId;
    }
}
