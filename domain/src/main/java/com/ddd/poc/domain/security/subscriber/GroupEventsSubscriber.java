package com.ddd.poc.domain.security.subscriber;

import com.ddd.poc.domain.security.repository.GroupDomainRepository;
import com.ddd.poc.domain.security.event.GroupCreatedEvent;
import com.ddd.poc.domain.security.event.GroupDeletedEvent;
import com.ddd.poc.domain.security.event.GroupUpdatedEvent;
import com.ddd.poc.domain.security.model.GroupDM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupEventsSubscriber {

    private final static Logger LOGGER = LoggerFactory.getLogger(GroupEventsSubscriber.class);

    private final GroupDomainRepository groupDomainRepository;

    @Autowired
    public GroupEventsSubscriber(GroupDomainRepository groupDomainRepository) {
        this.groupDomainRepository = groupDomainRepository;
    }

    @EventListener
    public void onGroupCreated(GroupCreatedEvent event) {
        LOGGER.info("Group created subscriber called");
        groupDomainRepository.create().save(event.getName());

    }

    @EventListener
    public void onGroupUpdated(GroupUpdatedEvent event) {
        LOGGER.info("Group updated subscriber called");
        Optional<GroupDM> groupDM = groupDomainRepository.find(event.getGroupId());
        groupDM.ifPresent(groupDMObj -> groupDMObj.save(event.getNewName()));
    }

    @EventListener
    public void onGroupDeleted(GroupDeletedEvent event) {
        LOGGER.info("Group deleted subscriber called");
        Optional<GroupDM> userDM = groupDomainRepository.find(event.getGroupId());
        userDM.ifPresent(GroupDM::delete);
    }
}
