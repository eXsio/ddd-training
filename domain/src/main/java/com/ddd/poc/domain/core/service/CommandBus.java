package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.command.DomainCommand;

public interface CommandBus {

    void publishCommand(DomainCommand domainCommand);
}
