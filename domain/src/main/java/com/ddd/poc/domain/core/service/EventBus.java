package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.dao.EventDomainDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventBus {

    private final static Logger LOGGER = LoggerFactory.getLogger(EventBus.class);

    private final ApplicationEventPublisher publisher;

    private final EventDomainDao eventDomainDao;

    @Autowired
    public EventBus(ApplicationEventPublisher publisher, EventDomainDao eventDomainDao) {
        this.publisher = publisher;
        this.eventDomainDao = eventDomainDao;
    }


    @Transactional
    public void publishEvent(DomainEvent event) {
        try {
            eventDomainDao.create(event).save();
            publisher.publishEvent(event);
        } catch (Exception ex) {
            LOGGER.error("An error occured during event publishing, event: {}, exception: {}", event, ex.getMessage(), ex);
        }
    }
}
