package com.ddd.poc.command.subscriber;

import com.ddd.poc.command.event.UserCreatedEvent;
import com.ddd.poc.command.event.UserDeletedEvent;
import com.ddd.poc.command.event.UserUpdatedEvent;
import com.ddd.poc.domain.security.dao.UserDomainDao;
import com.ddd.poc.domain.security.model.UserDM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserCommandSubscriber {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserCommandSubscriber.class);

    private final UserDomainDao userDomainDao;

    @Autowired
    public UserCommandSubscriber(UserDomainDao userDomainDao) {
        this.userDomainDao = userDomainDao;
    }

    @EventListener
    public void onUserCreated(UserCreatedEvent event) {

        LOGGER.info("User created subscriber called");
        UserDM userDM = userDomainDao.create();
        userDM.save(event.getUserDTO());
    }

    @EventListener
    public void onUserUpdated(UserUpdatedEvent event) {
        LOGGER.info("User updated subscriber called");
        UserDM userDM = userDomainDao.find(event.getUserDTO().getId());
        userDM.save(event.getUserDTO());
    }

    @EventListener
    public void onUserDeleted(UserDeletedEvent event) {
        LOGGER.info("User deleted subscriber called");
        UserDM userDM = userDomainDao.find(event.getUserId());
        userDM.delete();
    }
}
