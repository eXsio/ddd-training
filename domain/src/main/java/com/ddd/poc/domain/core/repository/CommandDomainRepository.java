package com.ddd.poc.domain.core.repository;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.model.CommandDM;
import com.ddd.poc.domain.core.model.CommandEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommandDomainRepository {

    private final CommandEntityRepository commandEntityRepository;

    private final EventDomainRepository eventDomainRepository;

    @Autowired
    public CommandDomainRepository(CommandEntityRepository commandEntityRepository, EventDomainRepository eventDomainRepository) {
        this.commandEntityRepository = commandEntityRepository;
        this.eventDomainRepository = eventDomainRepository;
    }

    public Optional<CommandDM> findByUuid(String uuid) {
        Optional<CommandEntity> commandEntity = commandEntityRepository.findOneByUuid(uuid);
        return commandEntity.map(commandEntityObj -> Optional.of(new CommandDM(commandEntityObj, commandEntityRepository, eventDomainRepository)))
                .orElse(Optional.<CommandDM>empty());
    }

    public <T extends DomainCommand> CommandDM<T> create(T command) {
        return new CommandDM<>(command, commandEntityRepository, eventDomainRepository);
    }
}
