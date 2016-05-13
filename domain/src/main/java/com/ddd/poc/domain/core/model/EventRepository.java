package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.dao.EventDao;
import com.ddd.poc.domain.core.event.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class EventRepository {

    private final EventDao eventDao;

    @Autowired
    public EventRepository(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public Collection<EventDM> findAll() {
        return eventDao.findAllOrderByCreatedAt().stream().map(eventEntity -> new EventDM<>(eventEntity)).collect(Collectors.toList());
    }

    public <E extends DomainEvent> EventDM<E> create(E event, CommandEntity commandEntity) {
        return new EventDM<>(event, commandEntity);
    }

    public <E extends DomainEvent> EventDM<E> save(EventDM<E> eventDM) {
        return new EventDM<>(eventDao.save(eventDM.getEntity()));
    }
}
