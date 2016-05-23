package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.annotation.Synchronous;
import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.service.queue.EventQueue;
import com.ddd.poc.domain.core.service.store.EventStore;
import com.ddd.poc.domain.core.util.DataConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Synchronous
public class SynchronousEventBusImpl implements EventBus {

    private final static Logger LOGGER = LoggerFactory.getLogger(SynchronousEventBusImpl.class);

    private final EventStore eventStore;

    private final EventQueue eventQueue;

    @Autowired
    public SynchronousEventBusImpl(EventStore eventStore, EventQueue eventQueue) {
        this.eventStore = eventStore;
        this.eventQueue = eventQueue;
    }

    @Override
    @Transactional
    public void publishEvent(DomainEvent domainEvent, DomainCommand sourceCommand) {
        try {
            LOGGER.info("publishing event - {}: {}", domainEvent.getClass().getName(), DataConverter.toString(domainEvent));
            eventStore.store(domainEvent, sourceCommand);
            eventQueue.send(domainEvent);
        } catch (Exception ex) {
            LOGGER.error("An error occurred during event publishing, event: {}, exception: {}", DataConverter.toString(domainEvent), ex.getMessage(), ex);
        }
    }
}
