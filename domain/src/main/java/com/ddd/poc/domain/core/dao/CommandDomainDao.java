package com.ddd.poc.domain.core.dao;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.model.CommandDM;
import com.ddd.poc.domain.core.model.CommandEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommandDomainDao {

    private final CommandEntityDao commandEntityDao;

    private final EventDomainDao eventDomainDao;

    @Autowired
    public CommandDomainDao(CommandEntityDao commandEntityDao, EventDomainDao eventDomainDao) {
        this.commandEntityDao = commandEntityDao;
        this.eventDomainDao = eventDomainDao;
    }

    public Optional<CommandDM> findByUuid(String uuid) {
        Optional<CommandEntity> commandEntity = commandEntityDao.findOneByUuid(uuid);
        return commandEntity.map(commandEntityObj -> Optional.of(new CommandDM(commandEntityObj, commandEntityDao, eventDomainDao)))
                .orElse(Optional.<CommandDM>empty());
    }

    public <T extends DomainCommand> CommandDM<T> create(T command) {
        return new CommandDM<>(command, commandEntityDao, eventDomainDao);
    }
}
