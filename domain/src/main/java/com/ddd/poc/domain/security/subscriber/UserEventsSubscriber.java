package com.ddd.poc.domain.security.subscriber;

import com.ddd.poc.domain.security.event.UserCreatedEvent;
import com.ddd.poc.domain.security.event.UserDeletedEvent;
import com.ddd.poc.domain.security.event.UserJoinedGroupEvent;
import com.ddd.poc.domain.security.event.UserLeftGroupEvent;
import com.ddd.poc.domain.security.event.UserUpdatedEvent;
import com.ddd.poc.domain.security.model.User;
import com.ddd.poc.domain.security.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserEventsSubscriber {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserEventsSubscriber.class);

    private final UserRepository userRepository;

    @Autowired
    public UserEventsSubscriber(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void onUserCreated(UserCreatedEvent event) {
        LOGGER.info("User created subscriber called");
        userRepository.save(userRepository.create().update(event.getUserDTO()));
    }

    @EventListener
    public void onUserUpdated(UserUpdatedEvent event) {
        LOGGER.info("User updated subscriber called");
        Optional<User> user = userRepository.find(event.getUserDTO().getId());
        user.ifPresent(userObj -> userRepository.save(userObj.update(event.getUserDTO())));
    }

    @EventListener
    public void onUserDeleted(UserDeletedEvent event) {
        LOGGER.info("User deleted subscriber called");
        Optional<User> user = userRepository.find(event.getUserId());
        user.ifPresent(userRepository::delete);
    }

    @EventListener
    public void onUserJoinedGroup(UserJoinedGroupEvent event) {
        LOGGER.info("User joined group subscriber called");
        Optional<User> user = userRepository.find(event.getUserId());
        user.ifPresent(userObj -> userRepository.save(userObj.joinGroup(event.getGroupId())));
    }

    @EventListener
    public void onUserLeftGroup(UserLeftGroupEvent event) {
        LOGGER.info("User left group subscriber called");
        Optional<User> user = userRepository.find(event.getUserId());
        user.ifPresent(userObj -> userRepository.save(userObj.leaveGroup(event.getGroupId())));
    }
}
