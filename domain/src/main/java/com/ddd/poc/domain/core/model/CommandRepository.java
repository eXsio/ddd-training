package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.dao.CommandDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommandRepository {

    private final CommandDao commandDao;

    private final EventRepository eventRepository;

    @Autowired
    public CommandRepository(CommandDao commandDao, EventRepository eventRepository) {
        this.commandDao = commandDao;
        this.eventRepository = eventRepository;
    }

    public Optional<CommandDM> findByUuid(String uuid) {
        Optional<CommandEntity> commandEntity = commandDao.findOneByUuid(uuid);
        return commandEntity.map(commandEntityObj -> Optional.of(new CommandDM(commandEntityObj, eventRepository)))
                .orElse(Optional.<CommandDM>empty());
    }

    public <T extends DomainCommand> CommandDM<T> create(T command) {
        return new CommandDM<>(command, eventRepository);
    }

    public <E extends DomainCommand> CommandDM<E> save(CommandDM<E> commandDM) {
        return new CommandDM<>(commandDao.save(commandDM.getEntity()), eventRepository);
    }
}
