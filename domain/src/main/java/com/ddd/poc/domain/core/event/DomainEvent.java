package com.ddd.poc.domain.core.event;

import java.util.UUID;

public abstract class DomainEvent {

    private final UUID uuid = UUID.randomUUID();

    public UUID getUuid() {
        return uuid;
    }
}
