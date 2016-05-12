package com.ddd.poc.domain.core.command;

import java.util.UUID;

public abstract class DomainCommand {

    private final UUID uuid = UUID.randomUUID();

    public UUID getUuid() {
        return uuid;
    }
}
