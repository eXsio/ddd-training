package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.dao.EventDomainDao;
import com.ddd.poc.domain.core.dao.EventEntityDao;
import com.ddd.poc.domain.core.event.TestEvent;
import com.ddd.poc.domain.core.model.EventDM;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

    private EventDM<TestEvent> eventDM;

    private EventBus underTest;

    private TestEvent event = new TestEvent("TEST_VALUE");

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        eventDM = spy(new EventDM<>(event, eventEntityDao));
        when(eventDomainDao.create(event)).thenReturn(eventDM);

        underTest = new EventBus(publisher, eventDomainDao);
    }

    @Test
    public void test_publishEvent() {
        underTest.publishEvent(event);

        verify(eventDomainDao).create(event);
        verify(eventDM).save();
        verify(publisher).publishEvent(event);

        verifyNoMoreInteractions(eventDomainDao);
        verifyNoMoreInteractions(publisher);
        verifyNoMoreInteractions(eventDM);
    }
}
