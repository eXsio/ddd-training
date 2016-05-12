package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.command.TestCommand;
import com.ddd.poc.domain.core.dao.EventEntityDao;
import com.ddd.poc.domain.core.event.TestEvent;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class EventDMTest {

    private final static String TEST_VALUE = "TEST_VALUE";

    @Mock
    private EventEntityDao eventEntityDao;

    private String uuid = UUID.randomUUID().toString();

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_createFromDataAndSave() {

        TestEvent event = new TestEvent();
        event.setTestField(TEST_VALUE);
        CommandEntity commandEntity = new CommandEntity(TestCommand.class.getCanonicalName(), TestCommand.serialized(TEST_VALUE), uuid);
        EventDM<TestEvent> result = new EventDM<>(event, commandEntity, eventEntityDao);

        assertEquals(result.getEventClass(), TestEvent.class);
        assertEquals(result.getData(), event);

        result.save();

        ArgumentCaptor<EventEntity> eventEntityArgumentCaptor = ArgumentCaptor.forClass(EventEntity.class);
        verify(eventEntityDao).save(eventEntityArgumentCaptor.capture());

        EventEntity entity = eventEntityArgumentCaptor.getValue();
        assertEquals(entity.getEventClass(), TestEvent.class.getCanonicalName());
        assertTrue(entity.getEventData().contains("TEST_VALUE"));
        assertNotNull(entity.getCreatedAt());
    }

    @Test
    public void test_createFromEntityAndSave() {

        TestEvent event = new TestEvent();
        event.setTestField(TEST_VALUE);
        CommandEntity commandEntity = new CommandEntity(TestCommand.serialized(TEST_VALUE), TestCommand.class.getCanonicalName(), uuid);
        EventEntity entity = new EventEntity(commandEntity, TestEvent.serialized("TEST_VALUE"), TestEvent.class.getCanonicalName(), uuid);
        EventDM<TestEvent> result = new EventDM<>(entity, eventEntityDao);

        assertEquals(result.getEventClass(), TestEvent.class);
        assertEquals(result.getData(), event);

        result.save();

        ArgumentCaptor<EventEntity> eventEntityArgumentCaptor = ArgumentCaptor.forClass(EventEntity.class);
        verify(eventEntityDao).save(eventEntityArgumentCaptor.capture());

        entity = eventEntityArgumentCaptor.getValue();
        assertEquals(entity.getEventClass(), TestEvent.class.getCanonicalName());
        assertEquals(entity.getEventData(), TestEvent.serialized("TEST_VALUE"));
        assertNotNull(entity.getCreatedAt());
    }


}
