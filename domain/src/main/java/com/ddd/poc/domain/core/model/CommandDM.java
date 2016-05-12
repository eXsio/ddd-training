package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.dao.CommandEntityDao;
import com.ddd.poc.domain.core.dao.EventDomainDao;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.ex.ClassNotFoundRuntimeException;
import com.ddd.poc.domain.core.service.DataConverter;
import com.google.common.base.Preconditions;
import org.springframework.transaction.annotation.Transactional;

public class CommandDM<C extends DomainCommand> {

    private final CommandEntity entity;

    private final CommandEntityDao commandEntityDao;

    private final EventDomainDao eventDomainDao;

    public CommandDM(C command, CommandEntityDao commandEntityDao, EventDomainDao eventDomainDao) {
        this(new CommandEntity(DataConverter.toString(command), command.getClass().getCanonicalName()), commandEntityDao, eventDomainDao);
    }

    public CommandDM(CommandEntity entity, CommandEntityDao commandEntityDao, EventDomainDao eventDomainDao) {

        Preconditions.checkNotNull(entity);
        Preconditions.checkNotNull(commandEntityDao);
        Preconditions.checkNotNull(eventDomainDao);

        this.entity = entity;
        this.commandEntityDao = commandEntityDao;
        this.eventDomainDao = eventDomainDao;
    }

    @Transactional
    public void save() {
        commandEntityDao.save(entity);
    }

    public Class<C> getCommandClass() {
        try {
            return (Class<C>) Class.forName(entity.getCommandClass());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(String.format("Command class %s was not found", entity.getCommandClass()));
        }
    }

    public <E extends DomainEvent> EventDM<E> createEvent(E event) {
        return eventDomainDao.create(event, entity);
    }

    public C getCommand() {
        return DataConverter.toObject(entity.getCommandData(), getCommandClass());
    }
}
