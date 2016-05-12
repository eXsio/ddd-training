package com.ddd.poc.command.security.command;

import com.ddd.poc.domain.core.command.DomainCommand;

public class CreateGroupCommand implements DomainCommand {

    private final String name;

    public CreateGroupCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
