package com.ddd.poc.domain.core.dao;

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

    public <T> EventDM<T> create(T data) {
        return new EventDM<>(data, eventEntityDao);
    }
}
