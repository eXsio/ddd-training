package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.annotation.Asynchronous;
import com.ddd.poc.domain.core.annotation.Synchronous;
import com.ddd.poc.domain.core.command.DomainCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Asynchronous
public class AsynchronousCommandBusImpl implements CommandBus {

    private final CommandBus commandBus;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Autowired
    public AsynchronousCommandBusImpl(@Synchronous CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Override
    @Transactional
    public void publishCommand(DomainCommand domainCommand) {
        executor.execute(() -> {
            commandBus.publishCommand(domainCommand);
        });
    }
}
