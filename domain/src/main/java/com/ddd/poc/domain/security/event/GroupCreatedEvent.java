package com.ddd.poc.domain.security.event;

import com.ddd.poc.domain.core.event.DomainEvent;

public class GroupCreatedEvent implements DomainEvent {

    private final String name;

    public GroupCreatedEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
