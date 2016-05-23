package com.ddd.poc.domain.security.subscriber;

import com.ddd.poc.domain.security.event.GroupCreatedEvent;
import com.ddd.poc.domain.security.event.GroupDeletedEvent;
import com.ddd.poc.domain.security.event.GroupUpdatedEvent;
import com.ddd.poc.domain.security.model.Group;
import com.ddd.poc.domain.security.model.GroupRepository;
import com.ddd.poc.domain.security.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class GroupEventsSubscriber {

    private final static Logger LOGGER = LoggerFactory.getLogger(GroupEventsSubscriber.class);

    private final GroupRepository groupRepository;

    @Autowired
    public GroupEventsSubscriber(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @EventListener
    public void onGroupCreated(GroupCreatedEvent event) {
        LOGGER.info("Group created subscriber called");
        ThreadUtil.simulateLatecy();
        groupRepository.save(groupRepository.create().update(event.getName()));

    }


    @EventListener
    public void onGroupUpdated(GroupUpdatedEvent event) {
        LOGGER.info("Group updated subscriber called");
        ThreadUtil.simulateLatecy();
        Optional<Group> group = groupRepository.find(event.getGroupId());
        group.ifPresent(groupObj -> groupRepository.save(groupObj.update(event.getNewName())));
    }

    @EventListener
    public void onGroupDeleted(GroupDeletedEvent event) {
        LOGGER.info("Group deleted subscriber called");
        ThreadUtil.simulateLatecy();
        Optional<Group> group = groupRepository.find(event.getGroupId());
        group.ifPresent(groupRepository::delete);
    }
}
