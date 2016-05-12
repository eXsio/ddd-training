package com.ddd.poc.domain.security.event;

import com.ddd.poc.domain.core.event.DomainEvent;

public class GroupDeletedEvent extends DomainEvent {

    private final Long groupId;

    public GroupDeletedEvent(Long groupId) {
        this.groupId = groupId;
    }


    public Long getGroupId() {
        return groupId;
    }
}
