package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.repository.EventEntityRepository;
import com.ddd.poc.domain.core.event.DomainEvent;
import com.ddd.poc.domain.core.ex.ClassNotFoundRuntimeException;
import com.ddd.poc.domain.core.util.DataConverter;
import com.google.common.base.Preconditions;
import org.springframework.transaction.annotation.Transactional;

public class EventDM<T extends DomainEvent> extends BaseAggregate<EventEntity> {


    private final EventEntityRepository eventEntityRepository;

    public EventDM(T event, CommandEntity commandEntity, EventEntityRepository eventEntityRepository) {
        this(new EventEntity(commandEntity, DataConverter.toString(event), event.getClass().getCanonicalName(), event.getUuid().toString()), eventEntityRepository);
    }

    public EventDM(EventEntity entity, EventEntityRepository eventEntityRepository) {
        super(entity);
        Preconditions.checkNotNull(eventEntityRepository);
        this.eventEntityRepository = eventEntityRepository;
    }

    @Transactional
    public EventDM save() {
        eventEntityRepository.save(entity);
        return this;
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
