package com.ddd.poc.domain.core.service.store;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;

public interface EventStore {

    void store(DomainEvent event, DomainCommand sourceCommand);
}
