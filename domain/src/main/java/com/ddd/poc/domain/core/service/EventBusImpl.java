package com.ddd.poc.domain.core.service;

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
public class EventBusImpl implements EventBus {

    private final static Logger LOGGER = LoggerFactory.getLogger(EventBusImpl.class);

    private final EventStore eventStore;

    private final EventQueue eventQueue;

    @Autowired
    public EventBusImpl(EventStore eventStore, EventQueue eventQueue) {
        this.eventStore = eventStore;
        this.eventQueue = eventQueue;
    }

    @Override
    @Transactional
    public void publishEvent(DomainEvent event, DomainCommand sourceCommand) {
        try {
            LOGGER.info("publishing event - {}: {}", event.getClass().getName(), DataConverter.toString(event));
            eventStore.store(event, sourceCommand);
            eventQueue.send(event);
        } catch (Exception ex) {
            LOGGER.error("An error occurred during event publishing, event: {}, exception: {}", DataConverter.toString(event), ex.getMessage(), ex);
        }
    }
}
