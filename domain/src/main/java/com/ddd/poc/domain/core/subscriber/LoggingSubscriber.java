package com.ddd.poc.domain.core.subscriber;

import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.util.DataConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class LoggingSubscriber {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoggingSubscriber.class);

    @EventListener
    public void onDomainEvent(DomainEvent domainEvent) {
        LOGGER.info("Domain event occurred - {}: {}", domainEvent.getClass().getName(), DataConverter.toString(domainEvent));
    }
}
