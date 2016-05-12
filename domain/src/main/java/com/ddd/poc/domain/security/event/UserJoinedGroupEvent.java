package com.ddd.poc.domain.security.event;

import com.ddd.poc.domain.core.event.DomainEvent;

public class UserJoinedGroupEvent implements DomainEvent {

    private final Long userId;

    private final Long groupId;

    public UserJoinedGroupEvent(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGroupId() {
        return groupId;
    }
}
