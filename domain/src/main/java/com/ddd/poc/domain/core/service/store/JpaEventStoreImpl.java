package com.ddd.poc.domain.core.service.store;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.ex.RelatedCommandNotFoundRuntimeException;
import com.ddd.poc.domain.core.model.Command;
import com.ddd.poc.domain.core.model.CommandRepository;
import com.ddd.poc.domain.core.model.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaEventStoreImpl implements EventStore {

    private final CommandRepository commandRepository;

    private final EventRepository eventRepository;

    @Autowired
    public JpaEventStoreImpl(CommandRepository commandRepository, EventRepository eventRepository) {
        this.commandRepository = commandRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void store(DomainEvent event, DomainCommand command) {
        eventRepository.save(getCommandModel(event, command).createEvent(event));
    }

    private Command getCommandModel(DomainEvent event, DomainCommand command) {
        Optional<Command> commandDM = commandRepository.findByUuid(command.getUuid().toString());
        return commandDM.map(commandDMObj -> commandDMObj)
                .orElseThrow(() -> new RelatedCommandNotFoundRuntimeException(
                        String.format("couldn't find stored command: %s related to event %s", command, event)));
    }
}
