package com.ddd.poc.domain.core.service.store;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;

public interface CommandStore {

    void store(DomainCommand sourceCommand);
}
