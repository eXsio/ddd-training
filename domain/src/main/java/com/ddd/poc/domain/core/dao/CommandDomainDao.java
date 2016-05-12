package com.ddd.poc.domain.core.dao;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.model.CommandDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CommandDomainDao {

    private final CommandEntityDao commandEntityDao;

    private final EventDomainDao eventDomainDao;

    @Autowired
    public CommandDomainDao(CommandEntityDao commandEntityDao, EventDomainDao eventDomainDao) {
        this.commandEntityDao = commandEntityDao;
        this.eventDomainDao = eventDomainDao;
    }

    public Collection<CommandDM> findAll() {
        return commandEntityDao.findAllOrderByCreatedAt().stream().map(commandEntity -> new CommandDM(commandEntity, commandEntityDao, eventDomainDao)).collect(Collectors.toList());
    }

    public <T extends DomainCommand> CommandDM<T> create(T command) {
        return new CommandDM<>(command, commandEntityDao, eventDomainDao);
    }
}
