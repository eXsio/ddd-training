package com.ddd.poc.command.security.service;

import com.ddd.poc.command.security.command.CreateGroupCommand;
import com.ddd.poc.command.security.command.DeleteGroupCommand;
import com.ddd.poc.command.security.command.UpdateGroupCommand;
import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.service.EventBus;
import com.ddd.poc.domain.security.dao.GroupEntityDao;
import com.ddd.poc.domain.security.event.GroupCreatedEvent;
import com.ddd.poc.domain.security.event.GroupDeletedEvent;
import com.ddd.poc.domain.security.event.GroupUpdatedEvent;
import com.ddd.poc.domain.security.event.UserLeftGroupEvent;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupService {

    private final EventBus eventBus;

    private final GroupEntityDao groupEntityDao;

    @Autowired
    public GroupService(EventBus eventBus, GroupEntityDao groupEntityDao) {
        this.eventBus = eventBus;
        this.groupEntityDao = groupEntityDao;
    }

    public void createGroup(CreateGroupCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new GroupCreatedEvent(command.getName()), command);
            sendNotificationEmail(command);
        }
    }

    public void updateGroup(UpdateGroupCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new GroupUpdatedEvent(command.getGroupId(), command.getNewName()), command);
            sendNotificationEmail(command);
        }
    }

    public void deleteGroup(DeleteGroupCommand command) {
        if (canPerformOperation(command)) {
            removeUsersFromGroup(command);
            eventBus.publishEvent(new GroupDeletedEvent(command.getGroupId()), command);
            sendNotificationEmail(command);
        }
    }

    private void removeUsersFromGroup(DeleteGroupCommand command) {
        groupEntityDao.findOne(command.getGroupId()).ifPresent(groupEntity -> {
            for (UserEntity userEntity : groupEntity.getUsers()) {
                eventBus.publishEvent(new UserLeftGroupEvent(userEntity.getId(), groupEntity.getId()), command);
            }
        });
    }

    private boolean canPerformOperation(DomainCommand command) {
        return true;
    }

    private void sendNotificationEmail(DomainCommand command) {
        //send email logic
    }
}
