package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.dao.EventEntityDao;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.ex.ClassNotFoundRuntimeException;
import com.ddd.poc.domain.core.service.DataConverter;
import com.google.common.base.Preconditions;
import org.springframework.transaction.annotation.Transactional;

public class EventDM<T extends DomainEvent> {

    private final EventEntity entity;

    private final EventEntityDao eventEntityDao;

    public EventDM(T data, CommandEntity commandEntity, EventEntityDao eventEntityDao) {
        this(new EventEntity(commandEntity, DataConverter.toString(data), data.getClass().getCanonicalName()), eventEntityDao);
    }

    public EventDM(EventEntity entity, EventEntityDao eventEntityDao) {
        Preconditions.checkNotNull(entity);
        Preconditions.checkNotNull(eventEntityDao);
        this.entity = entity;
        this.eventEntityDao = eventEntityDao;
    }

    @Transactional
    public void save() {
        eventEntityDao.save(entity);
    }

    public Class<T> getEventClass() {
        try {
            return (Class<T>) Class.forName(entity.getEventClass());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(String.format("Event class %s was not found", entity.getEventClass()));
        }
    }

    public T getData() {
        return DataConverter.toObject(entity.getEventData(), getEventClass());
    }
}
