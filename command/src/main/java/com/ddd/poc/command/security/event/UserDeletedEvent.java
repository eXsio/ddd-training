package com.ddd.poc.command.security.event;

import com.ddd.poc.domain.core.event.DomainEvent;

public class UserDeletedEvent implements DomainEvent {

    private final Long userId;

    public UserDeletedEvent(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
