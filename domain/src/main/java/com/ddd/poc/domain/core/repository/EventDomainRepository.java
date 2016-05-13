package com.ddd.poc.domain.core.repository;

import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.model.CommandEntity;
import com.ddd.poc.domain.core.model.EventDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class EventDomainRepository {

    private final EventEntityRepository eventEntityRepository;

    @Autowired
    public EventDomainRepository(EventEntityRepository eventEntityRepository) {
        this.eventEntityRepository = eventEntityRepository;
    }

    public Collection<EventDM> findAll() {
        return eventEntityRepository.findAllOrderByCreatedAt().stream().map(eventEntity -> new EventDM<>(eventEntity, eventEntityRepository)).collect(Collectors.toList());
    }

    public <E extends DomainEvent> EventDM<E> create(E event, CommandEntity commandEntity) {
        return new EventDM<>(event, commandEntity, eventEntityRepository);
    }
}
