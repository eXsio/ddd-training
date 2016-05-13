package com.ddd.poc.domain.core.service.store;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.repository.CommandDomainRepository;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.ex.RelatedCommandNotFoundRuntimeException;
import com.ddd.poc.domain.core.model.CommandDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaEventStoreImpl implements EventStore {

    private final CommandDomainRepository commandDomainDao;

    @Autowired
    public JpaEventStoreImpl(CommandDomainRepository commandDomainDao) {
        this.commandDomainDao = commandDomainDao;
    }

    @Override
    public void store(DomainEvent event, DomainCommand command) {
        getCommandModel(event, command).createEvent(event).save();
    }

    private CommandDM getCommandModel(DomainEvent event, DomainCommand command) {
        Optional<CommandDM> commandDM = commandDomainDao.findByUuid(command.getUuid().toString());
        return commandDM.map(commandDMObj -> commandDMObj)
                .orElseThrow(() -> new RelatedCommandNotFoundRuntimeException(
                        String.format("couldn't find stored command: %s related to event %s", command, event)));
    }
}
