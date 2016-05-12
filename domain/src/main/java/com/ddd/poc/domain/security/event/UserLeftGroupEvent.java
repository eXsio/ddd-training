package com.ddd.poc.domain.security.event;

import com.ddd.poc.domain.core.event.DomainEvent;

public class UserLeftGroupEvent implements DomainEvent {

    private final Long userId;

    private final Long groupId;

    public UserLeftGroupEvent(Long userId, Long groupId) {
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
