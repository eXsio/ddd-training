package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.command.TestCommand;
import com.ddd.poc.domain.core.model.CommandRepository;
import com.ddd.poc.domain.core.dao.CommandDao;
import com.ddd.poc.domain.core.model.EventRepository;
import com.ddd.poc.domain.core.dao.EventDao;
import com.ddd.poc.domain.core.event.TestEvent;
import com.ddd.poc.domain.core.model.CommandDM;
import com.ddd.poc.domain.core.model.CommandEntity;
import com.ddd.poc.domain.core.model.EventDM;
import com.ddd.poc.domain.core.service.queue.SpringDispatcherEventQueueImpl;
import com.ddd.poc.domain.core.service.store.JpaEventStoreImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EventBusTest {

    @Mock
    private ApplicationEventPublisher publisher;

    @Mock
    private EventDao eventDao;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CommandRepository commandRepository;

    @Mock
    private CommandDao commandEntityDao;

    private EventDM<TestEvent> eventDM;

    private CommandDM<TestCommand> commandDM;

    private EventBus underTest;

    private TestEvent event = new TestEvent("TEST_VALUE");

    private TestCommand command = new TestCommand("TEST_VALUE");

    private String uuid = UUID.randomUUID().toString();

    private CommandEntity commandEntity = new CommandEntity(TestCommand.class.getCanonicalName(), TestCommand.serialized("TEST_VALUE"), uuid);

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        eventDM = spy(new EventDM<>(event, commandEntity));
        commandDM = spy(new CommandDM<>(command, eventRepository));
        when(commandRepository.create(command)).thenReturn(commandDM);
        when(commandRepository.findByUuid(anyString())).thenReturn(Optional.of(commandDM));
        when(eventRepository.create(event, commandEntity)).thenReturn(eventDM);
        when(commandDM.createEvent(event)).thenReturn(eventDM);

        underTest = new EventBusImpl(new JpaEventStoreImpl(commandRepository, eventRepository), new SpringDispatcherEventQueueImpl(publisher));
    }

    @Test
    public void test_publishEvent() {
        underTest.publishEvent(event, command);

        verify(eventRepository).save(eventDM);
        verify(publisher).publishEvent(event);

    }
}
