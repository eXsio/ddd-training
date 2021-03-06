package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.command.TestCommand;
import com.ddd.poc.domain.core.dao.EventDao;
import com.ddd.poc.domain.core.event.TestEvent;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class EventDMTest {

    private final static String TEST_VALUE = "TEST_VALUE";

    @Mock
    private EventDao eventDao;

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
        Event<TestEvent> result = new Event<>(event, commandEntity);

        assertEquals(result.getEventClass(), TestEvent.class);
        assertEquals(result.getData(), event);

        EventEntity entity = result.getEntity();
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
        Event<TestEvent> result = new Event<>(entity);

        assertEquals(result.getEventClass(), TestEvent.class);
        assertEquals(result.getData(), event);

        entity = result.getEntity();

        assertEquals(entity.getEventClass(), TestEvent.class.getCanonicalName());
        assertEquals(entity.getEventData(), TestEvent.serialized("TEST_VALUE"));
        assertNotNull(entity.getCreatedAt());
    }


}
