package com.ddd.poc.domain.core.model;

import com.ddd.poc.domain.core.dao.EventEntityDao;
import com.ddd.poc.domain.core.event.TestEvent;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class EventDMTest {

    private final static String TEST_VALUE = "TEST_VALUE";

    @Mock
    private EventEntityDao eventEntityDao;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_createFromDataAndSave() {

        TestEvent event = new TestEvent();
        event.setTestField(TEST_VALUE);
        EventDM<TestEvent> result = new EventDM<>(event, eventEntityDao);

        assertEquals(result.getEventClass(), TestEvent.class);
        assertEquals(result.getData(), event);

        result.save();

        ArgumentCaptor<EventEntity> eventEntityArgumentCaptor = ArgumentCaptor.forClass(EventEntity.class);
        verify(eventEntityDao).save(eventEntityArgumentCaptor.capture());

        EventEntity entity = eventEntityArgumentCaptor.getValue();
        assertEquals(entity.getEventClass(), TestEvent.class.getCanonicalName());
        assertEquals(entity.getData(), TestEvent.serialized("TEST_VALUE"));
        assertNotNull(entity.getCreatedAt());
    }

    @Test
    public void test_createFromEntityAndSave() {

        TestEvent event = new TestEvent();
        event.setTestField(TEST_VALUE);

        EventEntity entity = new EventEntity(TestEvent.class.getCanonicalName(), TestEvent.serialized("TEST_VALUE"));
        EventDM<TestEvent> result = new EventDM<>(entity, eventEntityDao);

        assertEquals(result.getEventClass(), TestEvent.class);
        assertEquals(result.getData(), event);

        result.save();

        ArgumentCaptor<EventEntity> eventEntityArgumentCaptor = ArgumentCaptor.forClass(EventEntity.class);
        verify(eventEntityDao).save(eventEntityArgumentCaptor.capture());

        entity = eventEntityArgumentCaptor.getValue();
        assertEquals(entity.getEventClass(), TestEvent.class.getCanonicalName());
        assertEquals(entity.getData(), TestEvent.serialized("TEST_VALUE"));
        assertNotNull(entity.getCreatedAt());
    }


}
