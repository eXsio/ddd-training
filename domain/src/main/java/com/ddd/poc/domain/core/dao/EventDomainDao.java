package com.ddd.poc.domain.core.dao;

import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.model.CommandEntity;
import com.ddd.poc.domain.core.model.EventDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class EventDomainDao {

    private final EventEntityDao eventEntityDao;

    @Autowired
    public EventDomainDao(EventEntityDao eventEntityDao) {
        this.eventEntityDao = eventEntityDao;
    }

    public Collection<EventDM> findAll() {
        return eventEntityDao.findAllOrderByCreatedAt().stream().map(eventEntity -> new EventDM<>(eventEntity, eventEntityDao)).collect(Collectors.toList());
    }

    public <E extends DomainEvent> EventDM<E> create(E event, CommandEntity commandEntity) {
        return new EventDM<>(event, commandEntity, eventEntityDao);
    }
}
