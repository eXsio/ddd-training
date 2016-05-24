package com.ddd.poc.domain.core.command;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Optional;
import java.util.UUID;

public abstract class DomainCommand {

    private final UUID uuid = UUID.randomUUID();

    @JsonIgnore
    private Optional<DomainCommand> parentCommand = Optional.empty();

    public Optional<DomainCommand> getParentCommand() {
        return parentCommand;
    }

    public DomainCommand setParentCommand(DomainCommand parentCommand) {
        this.parentCommand = Optional.of(parentCommand);
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }
}
