package com.ddd.poc.domain.core.service.queue;

import com.ddd.poc.domain.core.event.DomainEvent;

public interface EventQueue {

    void send(DomainEvent event);
}
