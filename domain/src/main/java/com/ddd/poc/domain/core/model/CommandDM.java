package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.ex.ClassNotFoundRuntimeException;
import com.ddd.poc.domain.core.repository.CommandEntityRepository;
import com.ddd.poc.domain.core.repository.EventDomainRepository;
import com.ddd.poc.domain.core.util.DataConverter;
import com.google.common.base.Preconditions;
import org.springframework.transaction.annotation.Transactional;

public class CommandDM<C extends DomainCommand> extends BaseAggregate<CommandEntity> {

    private final CommandEntityRepository commandEntityRepository;

    private final EventDomainRepository eventDomainRepository;

    public CommandDM(C command, CommandEntityRepository commandEntityRepository, EventDomainRepository eventDomainRepository) {
        this(new CommandEntity(DataConverter.toString(command), command.getClass().getCanonicalName(), command.getUuid().toString()), commandEntityRepository, eventDomainRepository);
    }

    public CommandDM(CommandEntity entity, CommandEntityRepository commandEntityRepository, EventDomainRepository eventDomainRepository) {
        super(entity);
        Preconditions.checkNotNull(commandEntityRepository);
        Preconditions.checkNotNull(eventDomainRepository);

        this.commandEntityRepository = commandEntityRepository;
        this.eventDomainRepository = eventDomainRepository;
    }

    @Transactional
    public CommandDM save() {
        commandEntityRepository.save(entity);
        return this;
    }

    public Class<C> getCommandClass() {
        try {
            return (Class<C>) Class.forName(entity.getCommandClass());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(String.format("Command class %s was not found", entity.getCommandClass()));
        }
    }

    public <E extends DomainEvent> EventDM<E> createEvent(E event) {
        return eventDomainRepository.create(event, entity);
    }

    public C getCommand() {
        return DataConverter.toObject(entity.getCommandData(), getCommandClass());
    }
}
