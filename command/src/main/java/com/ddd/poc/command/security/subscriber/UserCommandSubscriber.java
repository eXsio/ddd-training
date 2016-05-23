package com.ddd.poc.command.security.subscriber;

import com.ddd.poc.command.security.command.CreateGroupCommand;
import com.ddd.poc.command.security.command.CreateUserCommand;
import com.ddd.poc.command.security.command.DeleteUserCommand;
import com.ddd.poc.command.security.command.JoinGroupCommand;
import com.ddd.poc.command.security.command.LeaveGroupCommand;
import com.ddd.poc.command.security.command.UpdateUserCommand;
import com.ddd.poc.domain.core.annotation.Asynchronous;
import com.ddd.poc.domain.core.annotation.Synchronous;
import com.ddd.poc.domain.core.command.DomainCommand;
import com.ddd.poc.domain.core.service.CommandBus;
import com.ddd.poc.domain.core.service.EventBus;
import com.ddd.poc.domain.security.dao.GroupDao;
import com.ddd.poc.domain.security.dao.UserDao;
import com.ddd.poc.domain.security.dto.UserDTO;
import com.ddd.poc.domain.security.event.UserCreatedEvent;
import com.ddd.poc.domain.security.event.UserDeletedEvent;
import com.ddd.poc.domain.security.event.UserUpdatedEvent;
import com.ddd.poc.domain.security.model.GroupEntity;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class UserCommandSubscriber {

    private final EventBus eventBus;

    private final CommandBus commandBus;

    private final GroupDao groupDao;

    private final UserDao userDao;

    @Autowired
    public UserCommandSubscriber(@Synchronous EventBus eventBus, @Synchronous CommandBus commandBus, GroupDao groupDao, UserDao userDao) {
        this.eventBus = eventBus;
        this.commandBus = commandBus;
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
                commandBus.publishCommand(new CreateGroupCommand(groupName).setParentCommand(command));
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
                commandBus.publishCommand(new LeaveGroupCommand(groupEntity.getId(), userEntity.getId()).setParentCommand(command));
            }
        });
    }

    private void addNewGroups(UserEntity userEntity, Collection<String> groups, DomainCommand command) {

        groups.forEach(groupName -> {
            Optional<GroupEntity> groupEntity = groupDao.findOneByName(groupName);
            if (groupEntity.isPresent()) {
                if (userCanJoinGroup(userEntity, groupEntity.get())) {
                    commandBus.publishCommand(new JoinGroupCommand(groupEntity.get().getId(), userEntity.getId()).setParentCommand(command));
                }
            } else {
                throw new RuntimeException(String.format("there is no group %s", groupName));
            }
        });
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
