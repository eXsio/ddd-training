package com.ddd.poc.domain.core.subscriber;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.util.DataConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class LoggingSubscriber {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoggingSubscriber.class);

    @EventListener
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void onDomainEvent(DomainEvent domainEvent) {
        LOGGER.info("Domain event occurred - {}: {}", domainEvent.getClass().getName(), DataConverter.toString(domainEvent));
    }

    @EventListener
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void onDomainCommand(DomainCommand domainCommand) {
        LOGGER.info("Domain command issued - {}: {}", domainCommand.getClass().getName(), DataConverter.toString(domainCommand));
    }
}
