package com.ddd.poc.domain.security.subscriber;

import com.ddd.poc.domain.security.dao.UserDomainDao;
import com.ddd.poc.domain.security.event.UserCreatedEvent;
import com.ddd.poc.domain.security.event.UserDeletedEvent;
import com.ddd.poc.domain.security.event.UserJoinedGroupEvent;
import com.ddd.poc.domain.security.event.UserLeftGroupEvent;
import com.ddd.poc.domain.security.event.UserUpdatedEvent;
import com.ddd.poc.domain.security.model.UserDM;
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

    private final UserDomainDao userDomainDao;

    @Autowired
    public UserEventsSubscriber(UserDomainDao userDomainDao) {
        this.userDomainDao = userDomainDao;
    }

    @EventListener
    public void onUserCreated(UserCreatedEvent event) {
        LOGGER.info("User created subscriber called");
        userDomainDao.create().save(event.getUserDTO());
    }

    @EventListener
    public void onUserUpdated(UserUpdatedEvent event) {
        LOGGER.info("User updated subscriber called");
        Optional<UserDM> userDM = userDomainDao.find(event.getUserDTO().getId());
        userDM.ifPresent(userDMObj -> userDMObj.save(event.getUserDTO()));
    }

    @EventListener
    public void onUserDeleted(UserDeletedEvent event) {
        LOGGER.info("User deleted subscriber called");
        Optional<UserDM> userDM = userDomainDao.find(event.getUserId());
        userDM.ifPresent(UserDM::delete);
    }

    @EventListener
    public void onUserJoinedGroup(UserJoinedGroupEvent event) {
        LOGGER.info("User joined group subscriber called");
        Optional<UserDM> userDM = userDomainDao.find(event.getUserId());
        userDM.ifPresent(userDMObj -> userDMObj.joinGroup(event.getGroupId()));
    }

    @EventListener
    public void onUserLeftGroup(UserLeftGroupEvent event) {
        LOGGER.info("User left group subscriber called");
        Optional<UserDM> userDM = userDomainDao.find(event.getUserId());
        userDM.ifPresent(userDMObj -> userDMObj.leaveGroup(event.getGroupId()));
    }
}
