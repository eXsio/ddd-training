package com.ddd.poc.domain.core.dao;

import com.beust.jcommander.internal.Lists;
import com.ddd.poc.domain.core.event.TestEvent;
import com.ddd.poc.domain.core.model.EventDM;
import com.ddd.poc.domain.core.model.EventEntity;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class EventDomainDaoTest {

    @Mock
    private EventEntityDao eventEntityDao;

    private EventDomainDao underTest;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(eventEntityDao.findAllOrderByCreatedAt()).thenReturn(Lists.newArrayList(
                new EventEntity(TestEvent.class.getCanonicalName(), TestEvent.serialized("TEST_VALUE1")),
                new EventEntity(TestEvent.class.getCanonicalName(), TestEvent.serialized("TEST_VALUE2"))
        ));
        underTest = new EventDomainDao(eventEntityDao);
    }

    @Test
    public void test_findAll() {
        Collection<EventDM> result = underTest.findAll();
        assertNotNull(result);
        assertEquals(result.size(), 2);
        Iterator<EventDM> iterator = result.iterator();
        EventDM event1 = iterator.next();
        assertEquals(event1.getData(), new TestEvent("TEST_VALUE1"));
        EventDM event2 = iterator.next();
        assertEquals(event2.getData(), new TestEvent("TEST_VALUE2"));

    }

    @Test
    public void test_create() {
        EventDM<TestEvent> result = underTest.create(new TestEvent("TEST_VALUE"));
        assertNotNull(result);
        assertEquals(result.getData(), new TestEvent("TEST_VALUE"));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void test_create_null_data() {
        underTest.create(null);
    }
}
