package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.annotation.Asynchronous;
import com.ddd.poc.domain.core.annotation.Synchronous;
import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Asynchronous
public class AsynchronousEventBusImpl implements EventBus {

    private final EventBus eventBus;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Autowired
    public AsynchronousEventBusImpl(@Synchronous EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    @Transactional
    public void publishEvent(DomainEvent domainEvent, DomainCommand sourceCommand) {
        executor.execute(() -> {
            eventBus.publishEvent(domainEvent, sourceCommand);
        });

    }
}
