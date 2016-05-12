package com.ddd.poc.command.security.service;

import com.ddd.poc.command.security.command.CreateGroupCommand;
import com.ddd.poc.command.security.command.DeleteGroupCommand;
import com.ddd.poc.command.security.command.UpdateGroupCommand;
import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.service.EventBus;
import com.ddd.poc.domain.security.event.GroupCreatedEvent;
import com.ddd.poc.domain.security.event.GroupDeletedEvent;
import com.ddd.poc.domain.security.event.GroupUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final EventBus eventBus;

    @Autowired
    public GroupService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void createGroup(CreateGroupCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new GroupCreatedEvent(command.getName()), command);
            sendNotificationEmal(command);
        }
    }

    public void updateGroup(UpdateGroupCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new GroupUpdatedEvent(command.getGroupId(), command.getNewName()), command);
            sendNotificationEmal(command);
        }
    }

    public void deleteGroup(DeleteGroupCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new GroupDeletedEvent(command.getGroupId()), command);
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
