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

    public Collection<Event> findAll() {
        return eventDao.findAllOrderByCreatedAt().stream().map(eventEntity -> new Event<>(eventEntity)).collect(Collectors.toList());
    }

    public <E extends DomainEvent> Event<E> create(E domainEvent, CommandEntity commandEntity) {
        return new Event<>(domainEvent, commandEntity);
    }

    public <E extends DomainEvent> Event<E> save(Event<E> event) {
        return new Event<>(eventDao.save(event.getEntity()));
    }
}
