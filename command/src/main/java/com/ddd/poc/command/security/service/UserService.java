package com.ddd.poc.command.security.service;

import com.ddd.poc.command.security.command.CreateUserCommand;
import com.ddd.poc.command.security.command.DeleteUserCommand;
import com.ddd.poc.command.security.command.UpdateUserCommand;
import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.service.EventBus;
import com.ddd.poc.domain.security.dao.GroupEntityDao;
import com.ddd.poc.domain.security.dao.UserEntityDao;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final EventBus eventBus;

    private final GroupEntityDao groupEntityDao;

    private final UserEntityDao userEntityDao;

    @Autowired
    public UserService(EventBus eventBus, GroupEntityDao groupEntityDao, UserEntityDao userEntityDao) {
        this.eventBus = eventBus;
        this.groupEntityDao = groupEntityDao;
        this.userEntityDao = userEntityDao;
    }

    public void createUser(CreateUserCommand command) {
        if (canPerformOperation(command)) {
            createGroupsIfNeeded(command.getUserDTO(), command);
            eventBus.publishEvent(new UserCreatedEvent(command.getUserDTO()), command);
            updateGroups(command.getUserDTO(), command);
            sendNotificationEmail(command);
        }
    }

    public void updateUser(UpdateUserCommand command) {
        if (canPerformOperation(command)) {
            createGroupsIfNeeded(command.getUserDTO(), command);
            eventBus.publishEvent(new UserUpdatedEvent(command.getUserDTO()), command);
            updateGroups(command.getUserDTO(), command);
            sendNotificationEmail(command);
        }
    }

    public void deleteUser(DeleteUserCommand command) {
        if (canPerformOperation(command)) {
            eventBus.publishEvent(new UserDeletedEvent(command.getUserId()), command);
            sendNotificationEmail(command);
        }
    }

    private void createGroupsIfNeeded(UserDTO userDTO, DomainCommand command) {
        for (String groupName : userDTO.getGroups()) {
            if (!groupEntityDao.findByName(groupName).isPresent()) {
                eventBus.publishEvent(new GroupCreatedEvent(groupName), command);
            }
        }
    }

    private void updateGroups(UserDTO data, DomainCommand command) {
        Optional<UserEntity> userEntity = userEntityDao.findOneByUsername(data.getUsername());
        userEntity.ifPresent(userEntityObj -> {
            removeDeletedGroups(userEntityObj, data.getGroups(), command);
            addNewGroups(userEntityObj, data.getGroups(), command);
        });
    }

    private void removeDeletedGroups(UserEntity userEntity, Collection<String> groups, DomainCommand command) {

        Iterator<GroupEntity> groupEntityIterator = userEntity.getGroups().iterator();
        while (groupEntityIterator.hasNext()) {
            GroupEntity groupEntity = groupEntityIterator.next();
            if (!groups.contains(groupEntity.getName())) {
                eventBus.publishEvent(new UserLeftGroupEvent(userEntity.getId(), groupEntity.getId()), command);
            }
        }
    }

    private void addNewGroups(UserEntity userEntity, Collection<String> groups, DomainCommand command) {
        for (String groupName : groups) {
            Optional<GroupEntity> group = groupEntityDao.findByName(groupName);
            group.ifPresent(groupEntity ->
            {
                if (userCanJoinGroup(userEntity, groupEntity)) {
                    eventBus.publishEvent(new UserJoinedGroupEvent(userEntity.getId(), groupEntity.getId()), command);
                }
            });
        }
    }

    private boolean canPerformOperation(DomainCommand command) {
        return true;
    }

    private boolean userCanJoinGroup(UserEntity userEntity, GroupEntity groupEntity) {
        return true;
    }

    private void sendNotificationEmail(DomainCommand command) {
        //send email logic
    }
}
