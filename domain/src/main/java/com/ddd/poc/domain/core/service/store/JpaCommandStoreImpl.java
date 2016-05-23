package com.ddd.poc.domain.core.service.store;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.ex.CommandStoreRuntimeException;
import com.ddd.poc.domain.core.model.Command;
import com.ddd.poc.domain.core.model.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaCommandStoreImpl implements CommandStore {

    private final CommandRepository commandRepository;

    @Autowired
    public JpaCommandStoreImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Override
    public void store(DomainCommand command) {
        try {
            Optional<Command> commandDM = commandRepository.findByUuid(command.getUuid().toString());
            if (!commandDM.isPresent()) {
                Command newCommandDM = command.getParentCommand()
                        .map(parentCommand -> createCommandWithParent(command, parentCommand))
                        .orElse(commandRepository.create(command));
                commandRepository.save(newCommandDM);
            }
        } catch (Exception ex) {
            throw new CommandStoreRuntimeException(String.format("Couldn't store command: %s", command), ex);
        }
    }

    private Command createCommandWithParent(DomainCommand command, DomainCommand parentCommand) {
        Optional<Command> parentCommandDM = commandRepository.findByUuid(parentCommand.getUuid().toString());
        if (parentCommandDM.isPresent()) {
            return commandRepository.create(command, parentCommandDM.get());
        } else {
            throw new CommandStoreRuntimeException(String.format("Couldn't find parent command: %s", command));
        }
    }
}
