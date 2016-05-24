package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.annotation.Synchronous;
import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.command.ReportApplicationErrorCommand;
import com.ddd.poc.domain.core.service.queue.CommandQueue;
import com.ddd.poc.domain.core.service.store.CommandStore;
import com.ddd.poc.domain.core.util.DataConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Synchronous
public class SynchronousCommandBusImpl implements CommandBus {

    private final static Logger LOGGER = LoggerFactory.getLogger(SynchronousCommandBusImpl.class);

    private final CommandStore commandStore;

    private final CommandQueue commandQueue;

    private final ApplicationErrorReporter errorReporter;

    @Autowired
    public SynchronousCommandBusImpl(CommandStore commandStore, CommandQueue commandQueue, ApplicationErrorReporter errorReporter) {
        this.commandStore = commandStore;
        this.commandQueue = commandQueue;
        this.errorReporter = errorReporter;
    }

    @Override
    @Transactional
    public void publishCommand(DomainCommand domainCommand) {
        try {
            LOGGER.info("publishing command - {}: {}", domainCommand.getClass().getName(), DataConverter.toString(domainCommand));
            commandStore.store(domainCommand);
            commandQueue.send(domainCommand);
        } catch (RuntimeException ex) {
            if (!(domainCommand instanceof ReportApplicationErrorCommand)) {
                errorReporter.reportApplicationError(ex, domainCommand, this);
            }
            LOGGER.error("An error occurred during command publishing, command: {}, exception: {}", DataConverter.toString(domainCommand), ex.getMessage());
            throw ex;
        }
    }
}
