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

    public Optional<Command> findByUuid(String uuid) {
        Optional<CommandEntity> commandEntity = commandDao.findOneByUuid(uuid);
        return commandEntity.map(commandEntityObj -> Optional.of(new Command(commandEntityObj, eventRepository)))
                .orElse(Optional.<Command>empty());
    }

    public <T extends DomainCommand> Command<T> create(T domainCommand) {
        return new Command<>(domainCommand, eventRepository);
    }

    public <T extends DomainCommand> Command<T> create(T domainCommand, Command parentCommand) {
        return new Command<>(domainCommand, parentCommand, eventRepository);
    }

    public <E extends DomainCommand> Command<E> save(Command<E> command) {
        return new Command<>(commandDao.save(command.getEntity()), eventRepository);
    }
}
