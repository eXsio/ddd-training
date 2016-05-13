package com.ddd.poc.command.security.subscriber;

import com.ddd.poc.command.security.command.CreateUserCommand;
import com.ddd.poc.command.security.command.DeleteUserCommand;
import com.ddd.poc.command.security.command.UpdateUserCommand;
import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.service.EventBus;
import com.ddd.poc.domain.security.dao.GroupDao;
import com.ddd.poc.domain.security.dao.UserDao;
import com.ddd.poc.domain.security.dto.UserDTO;
import com.ddd.poc.domain.security.event.GroupCreatedEvent;
import com.ddd.poc.domain.security.event.UserCreatedEvent;
import com.ddd.poc.domain.security.event.UserDeletedEvent;
import com.ddd.poc.domain.security.event.UserJoinedGroupEvent;
import com.ddd.poc.domain.security.event.UserLeftGroupEvent;
import com.ddd.poc.domain.security.event.UserUpdatedEvent;
import com.ddd.poc.domain.security.model.GroupEntity;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class UserCommandSubscriber {

    private final EventBus eventBus;

    private final GroupDao groupDao;

    private final UserDao userDao;

    @Autowired
    public UserCommandSubscriber(EventBus eventBus, GroupDao groupDao, UserDao userDao) {
        this.eventBus = eventBus;
        this.groupDao = groupDao;
        this.userDao = userDao;
    }

    @EventListener
    public void onCreateUserCommand(CreateUserCommand command) {
        if (canPerformOperation(command)) {
            createGroupsIfNeeded(command.getUserDTO(), command);
            eventBus.publishEvent(new UserCreatedEvent(command.getUserDTO()), command);
            updateGroups(command.getUserDTO(), command);
            sendNotificationEmail(command);
        }
    }

    @EventListener
    public void onUpdateUserCommand(UpdateUserCommand command) {
        if (canPerformOperation(command)) {
            createGroupsIfNeeded(command.getUserDTO(), command);
            eventBus.publishEvent(new UserUpdatedEvent(command.getUserDTO()), command);
            updateGroups(command.getUserDTO(), command);
            sendNotificationEmail(command);
        }
    }

    @EventListener
    public void onDeleteUserCommand(DeleteUserCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new UserDeletedEvent(command.getUserId()), command);
            sendNotificationEmail(command);
        }
    }

    private void createGroupsIfNeeded(UserDTO userDTO, DomainCommand command) {
        userDTO.getGroups().forEach(groupName -> {
            if (!groupDao.findOneByName(groupName).isPresent()) {
                eventBus.publishEvent(new GroupCreatedEvent(groupName), command);
            }
        });
    }

    private void updateGroups(UserDTO data, DomainCommand command) {
        userDao.findOneByUsername(data.getUsername()).ifPresent(userEntityObj -> {
            removeDeletedGroups(userEntityObj, data.getGroups(), command);
            addNewGroups(userEntityObj, data.getGroups(), command);
        });
    }

    private void removeDeletedGroups(UserEntity userEntity, Collection<String> groups, DomainCommand command) {
        userEntity.getGroups().forEach(groupEntity -> {
            if (!groups.contains(groupEntity.getName())) {
                eventBus.publishEvent(new UserLeftGroupEvent(userEntity.getId(), groupEntity.getId()), command);
            }
        });
    }

    private void addNewGroups(UserEntity userEntity, Collection<String> groups, DomainCommand command) {
        groups.forEach(groupName -> groupDao.findOneByName(groupName).ifPresent(groupEntity ->
        {
            if (userCanJoinGroup(userEntity, groupEntity)) {
                eventBus.publishEvent(new UserJoinedGroupEvent(userEntity.getId(), groupEntity.getId()), command);
            }
        }));
    }

    private boolean canPerformOperation(DomainCommand command) {
        //TODO: implement method
        return true;
    }

    private boolean userCanJoinGroup(UserEntity userEntity, GroupEntity groupEntity) {
        //TODO: implement method
        return true;
    }

    private void sendNotificationEmail(DomainCommand command) {
        //TODO: implement method
    }
}
