package com.ddd.poc.command.security.service;

import com.ddd.poc.command.security.command.CreateUserCommand;
import com.ddd.poc.command.security.command.DeleteUserCommand;
import com.ddd.poc.command.security.command.UpdateUserCommand;
import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.service.EventBus;
import com.ddd.poc.domain.security.event.UserCreatedEvent;
import com.ddd.poc.domain.security.event.UserDeletedEvent;
import com.ddd.poc.domain.security.event.UserUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final EventBus eventBus;

    @Autowired
    public UserService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void createUser(CreateUserCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new UserCreatedEvent(command.getUserDTO()), command);
            sendNotificationEmal(command);
        }
    }

    public void updateUser(UpdateUserCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new UserUpdatedEvent(command.getUserDTO()), command);
            sendNotificationEmal(command);
        }
    }

    public void deleteUser(DeleteUserCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new UserDeletedEvent(command.getUserId()), command);
            sendNotificationEmal(command);
        }
    }

    private boolean canPerformOperation(DomainCommand command) {
        return true;
    }

    private void sendNotificationEmal(DomainCommand command) {
        //send email logic
    }
}
