package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.command.TestCommand;
import com.ddd.poc.domain.core.dao.CommandDomainDao;
import com.ddd.poc.domain.core.dao.CommandEntityDao;
import com.ddd.poc.domain.core.dao.EventDomainDao;
import com.ddd.poc.domain.core.dao.EventEntityDao;
import com.ddd.poc.domain.core.event.TestEvent;
import com.ddd.poc.domain.core.model.CommandDM;
import com.ddd.poc.domain.core.model.CommandEntity;
import com.ddd.poc.domain.core.model.EventDM;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class EventBusTest {

    @Mock
    private ApplicationEventPublisher publisher;

    @Mock
    private EventEntityDao eventEntityDao;

    @Mock
    private EventDomainDao eventDomainDao;

    @Mock
    private CommandDomainDao commandDomainDao;

    @Mock
    private CommandEntityDao commandEntityDao;

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
        eventDM = spy(new EventDM<>(event, commandEntity, eventEntityDao));
        commandDM = spy(new CommandDM<>(command, commandEntityDao, eventDomainDao));
        when(commandDomainDao.create(command)).thenReturn(commandDM);
        when(commandDomainDao.findByUuid(anyString())).thenReturn(Optional.<CommandDM>empty());
        when(eventDomainDao.create(event, commandEntity)).thenReturn(eventDM);
        when(commandDM.createEvent(event)).thenReturn(eventDM);

        underTest = new EventBus(publisher, commandDomainDao);
    }

    @Test
    public void test_publishEvent() {
        underTest.publishEvent(event, command);

        verify(eventDM).save();
        verify(publisher).publishEvent(event);

    }
}
