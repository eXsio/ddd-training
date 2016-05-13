package com.ddd.poc.domain.core.service.store;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.ex.CommandStoreRuntimeException;
import com.ddd.poc.domain.core.model.CommandDM;
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
            Optional<CommandDM> commandDM = commandRepository.findByUuid(command.getUuid().toString());
            if (!commandDM.isPresent()) {
                commandRepository.save(commandRepository.create(command));
            }
        } catch (Exception ex) {
            throw new CommandStoreRuntimeException(String.format("Couldn't store command: %s", command), ex);
        }
    }
}
