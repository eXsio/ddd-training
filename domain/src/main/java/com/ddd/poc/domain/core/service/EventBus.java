package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;

public interface EventBus {

    public void publishEvent(DomainEvent event, DomainCommand sourceCommand);
}
