package com.ddd.poc.domain.core.service.queue;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;

public interface CommandQueue {

    void send(DomainCommand command);
}
