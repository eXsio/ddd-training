package com.ddd.poc.domain.core.dao;

import com.beust.jcommander.internal.Lists;
import com.ddd.poc.domain.core.command.TestCommand;
import com.ddd.poc.domain.core.event.TestEvent;
import com.ddd.poc.domain.core.model.CommandEntity;
import com.ddd.poc.domain.core.model.Event;
import com.ddd.poc.domain.core.model.EventRepository;
import com.ddd.poc.domain.core.model.EventEntity;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class EventDomainDaoTest {

    @Mock
    private EventDao eventEntityDao;

    private EventRepository underTest;

    private String uuid = UUID.randomUUID().toString();

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        CommandEntity commandEntity = new CommandEntity(TestCommand.class.getCanonicalName(), TestCommand.serialized("TEST_VALUE"), uuid);
        when(eventEntityDao.findAllOrderByCreatedAt()).thenReturn(Lists.newArrayList(
                new EventEntity(commandEntity, TestEvent.serialized("TEST_VALUE1"), TestEvent.class.getCanonicalName(), uuid),
                new EventEntity(commandEntity, TestEvent.serialized("TEST_VALUE2"), TestEvent.class.getCanonicalName(), uuid)
        ));
        underTest = new EventRepository(eventEntityDao);
    }

    @Test
    public void test_findAll() {
        Collection<Event> result = underTest.findAll();
        assertNotNull(result);
        assertEquals(result.size(), 2);
        Iterator<Event> iterator = result.iterator();
        Event event1 = iterator.next();
        assertEquals(event1.getData(), new TestEvent("TEST_VALUE1"));
        Event event2 = iterator.next();
        assertEquals(event2.getData(), new TestEvent("TEST_VALUE2"));

    }

    @Test
    public void test_create() {
        CommandEntity commandEntity = new CommandEntity(TestCommand.class.getCanonicalName(), TestCommand.serialized("TEST_VALUE"), uuid);
        Event<TestEvent> result = underTest.create(new TestEvent("TEST_VALUE"), commandEntity);
        assertNotNull(result);
        assertEquals(result.getData(), new TestEvent("TEST_VALUE"));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void test_create_null_data() {
        underTest.create(null, null);
    }
}
