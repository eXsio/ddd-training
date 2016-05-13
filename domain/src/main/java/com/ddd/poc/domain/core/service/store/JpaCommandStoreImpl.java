package com.ddd.poc.domain.core.service.store;

import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.dao.CommandDomainDao;
import com.ddd.poc.domain.core.ex.CommandStoreRuntimeException;
import com.ddd.poc.domain.core.model.CommandDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaCommandStoreImpl implements CommandStore {

    private final CommandDomainDao commandDomainDao;

    @Autowired
    public JpaCommandStoreImpl(CommandDomainDao commandDomainDao) {
        this.commandDomainDao = commandDomainDao;
    }

    @Override
    public void store(DomainCommand command) {
        try {
            Optional<CommandDM> commandDM = commandDomainDao.findByUuid(command.getUuid().toString());
            if (!commandDM.isPresent()) {
                CommandDM newCommandDM = commandDomainDao.create(command);
                newCommandDM.save();
            }
        } catch (Exception ex) {
            throw new CommandStoreRuntimeException(String.format("Couldn't store command: %s", command), ex);
        }
    }
}
