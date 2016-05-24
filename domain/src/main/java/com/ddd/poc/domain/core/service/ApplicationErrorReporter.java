package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.command.ReportApplicationErrorCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationErrorReporter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationErrorReporter.class);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void reportApplicationError(Throwable exception, DomainCommand sourceCommand, CommandBus commandBus) {
        try {
            commandBus.publishCommand(new ReportApplicationErrorCommand(exception, sourceCommand));
        } catch (Exception ex) {
            LOGGER.error("An error occurred while trying to report application error. Original exception: {}, reporting exception: {}", exception, ex);
        }
    }
}
