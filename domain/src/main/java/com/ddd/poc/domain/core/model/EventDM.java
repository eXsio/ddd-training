package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.ex.ClassNotFoundRuntimeException;
import com.ddd.poc.domain.core.util.DataConverter;

public class EventDM<T extends DomainEvent> extends BaseAggregate<EventEntity> {

    public EventDM(T event, CommandEntity commandEntity) {
        this(new EventEntity(commandEntity, DataConverter.toString(event), event.getClass().getCanonicalName(), event.getUuid().toString()));
    }

    public EventDM(EventEntity entity) {
        super(entity);
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

    @Override
    protected EventEntity getEntity() {
        return entity;
    }
}
