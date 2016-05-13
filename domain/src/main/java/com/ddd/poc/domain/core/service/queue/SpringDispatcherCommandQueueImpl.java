package com.ddd.poc.domain.core.service.queue;

import com.ddd.poc.domain.core.command.DomainCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SpringDispatcherCommandQueueImpl implements CommandQueue {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public SpringDispatcherCommandQueueImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void send(DomainCommand command) {
        publisher.publishEvent(command);
    }
}
