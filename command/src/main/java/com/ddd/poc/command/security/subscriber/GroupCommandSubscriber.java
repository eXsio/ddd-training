package com.ddd.poc.command.security.subscriber;

import com.ddd.poc.command.security.command.CreateGroupCommand;
import com.ddd.poc.command.security.command.DeleteGroupCommand;
import com.ddd.poc.command.security.command.JoinGroupCommand;
import com.ddd.poc.command.security.command.LeaveGroupCommand;
import com.ddd.poc.command.security.command.UpdateGroupCommand;
import com.ddd.poc.domain.core.annotation.Asynchronous;
import com.ddd.poc.domain.core.annotation.Synchronous;
import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.service.EventBus;
import com.ddd.poc.domain.security.dao.GroupDao;
import com.ddd.poc.domain.security.event.GroupCreatedEvent;
import com.ddd.poc.domain.security.event.GroupDeletedEvent;
import com.ddd.poc.domain.security.event.GroupUpdatedEvent;
import com.ddd.poc.domain.security.event.UserJoinedGroupEvent;
import com.ddd.poc.domain.security.event.UserLeftGroupEvent;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupCommandSubscriber {

    private final EventBus eventBus;

    private final GroupDao groupDao;

    @Autowired
    public GroupCommandSubscriber(@Synchronous EventBus eventBus, GroupDao groupDao) {
        this.eventBus = eventBus;
        this.groupDao = groupDao;
    }

    @EventListener
    public void onCreateGroupCommand(CreateGroupCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new GroupCreatedEvent(command.getName()), command);
            sendNotificationEmail(command);
        }
    }

    @EventListener
    public void onUpdateGroupCommand(UpdateGroupCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new GroupUpdatedEvent(command.getGroupId(), command.getNewName()), command);
            sendNotificationEmail(command);
        }
    }

    @EventListener
    public void onDeleteGroupCommand(DeleteGroupCommand command) {
        if (canPerformOperation(command)) {
            removeUsersFromGroup(command);
            eventBus.publishEvent(new GroupDeletedEvent(command.getGroupId()), command);
            sendNotificationEmail(command);
        }
    }

    @EventListener
    public void onJoinGroupCommand(JoinGroupCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new UserJoinedGroupEvent(command.getUserId(), command.getGroupId()), command);
        }
    }

    @EventListener
    public void onLeaveGroupCommand(LeaveGroupCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new UserLeftGroupEvent(command.getUserId(), command.getGroupId()), command);
        }
    }

    private void removeUsersFromGroup(DeleteGroupCommand command) {
        groupDao.findOne(command.getGroupId()).ifPresent(groupEntity -> {
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
