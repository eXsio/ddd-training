package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.dao.CommandDomainDao;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.model.CommandDM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventBus {

    private final static Logger LOGGER = LoggerFactory.getLogger(EventBus.class);

    private final ApplicationEventPublisher publisher;

    private final CommandDomainDao commandDomainDao;

    @Autowired
    public EventBus(ApplicationEventPublisher publisher, CommandDomainDao commandDomainDao) {
        this.publisher = publisher;
        this.commandDomainDao = commandDomainDao;
    }

    @Transactional
    public void publishEvent(DomainEvent event, DomainCommand sourceCommand) {
        try {
            CommandDM commandDM = getOrCreate(sourceCommand);
            commandDM.createEvent(event).save();
            publisher.publishEvent(event);
        } catch (Exception ex) {
            LOGGER.error("An error occurred during event publishing, event: {}, exception: {}", event, ex.getMessage(), ex);
        }
    }

    private CommandDM getOrCreate(DomainCommand command) {
        Optional<CommandDM> commandDM = commandDomainDao.findByUuid(command.getUuid().toString());
        return commandDM.map(commandDMObj -> commandDMObj).orElseGet(() -> {
            CommandDM newCommandDM = commandDomainDao.create(command);
            newCommandDM.save();
            return newCommandDM;
        });
    }
}
