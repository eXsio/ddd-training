package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.ex.ClassNotFoundRuntimeException;
import com.ddd.poc.domain.core.util.DataConverter;
import com.google.common.base.Preconditions;

public class Command<C extends DomainCommand> extends BaseAggregate<CommandEntity> {

    private final EventRepository eventRepository;

    public Command(C command, EventRepository eventRepository) {
        this(new CommandEntity(DataConverter.toString(command), command.getClass().getCanonicalName(), command.getUuid().toString()), eventRepository);
    }

    public Command(C command, Command parentCommand, EventRepository eventRepository) {
        this(new CommandEntity(DataConverter.toString(command), command.getClass().getCanonicalName(), command.getUuid().toString()), eventRepository);
        entity.setParentCommand(parentCommand.getEntity());
    }

    public Command(CommandEntity entity, EventRepository eventRepository) {
        super(entity);
        Preconditions.checkNotNull(eventRepository);

        this.eventRepository = eventRepository;
    }

    public Class<C> getCommandClass() {
        try {
            return (Class<C>) Class.forName(entity.getCommandClass());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(String.format("Command class %s was not found", entity.getCommandClass()));
        }
    }

    public <E extends DomainEvent> Event<E> createEvent(E event) {
        return eventRepository.create(event, entity);
    }

    public C getCommand() {
        return DataConverter.toObject(entity.getCommandData(), getCommandClass());
    }

    @Override
    protected CommandEntity getEntity() {
        return entity;
    }
}
