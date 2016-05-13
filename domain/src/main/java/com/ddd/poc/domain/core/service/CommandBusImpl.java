package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.service.queue.CommandQueue;
import com.ddd.poc.domain.core.service.store.CommandStore;
import com.ddd.poc.domain.core.util.DataConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommandBusImpl implements CommandBus {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommandBusImpl.class);

    private final CommandStore commandStore;

    private final CommandQueue commandQueue;

    @Autowired
    public CommandBusImpl(CommandStore commandStore, CommandQueue commandQueue) {
        this.commandStore = commandStore;
        this.commandQueue = commandQueue;
    }

    @Override
    @Transactional
    public void publishCommand(DomainCommand command) {
        try {
            LOGGER.info("publishing command - {}: {}", command.getClass().getName(), DataConverter.toString(command));
            commandStore.store(command);
            commandQueue.send(command);
        } catch (Exception ex) {
            LOGGER.error("An error occurred during command publishing, command: {}, exception: {}", DataConverter.toString(command), ex.getMessage(), ex);
        }
    }
}
