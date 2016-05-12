package com.ddd.poc.command.security.command;

import com.ddd.poc.domain.core.command.DomainCommand;

public class DeleteUserCommand implements DomainCommand {

    private final Long userId;


    public DeleteUserCommand(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
