package com.ddd.poc.domain.core.service.queue;

import com.ddd.poc.domain.core.event.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SpringDispatcherEventQueueImpl implements EventQueue {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public SpringDispatcherEventQueueImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void send(DomainEvent event) {
        publisher.publishEvent(event);
    }
}
