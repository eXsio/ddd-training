package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.event.TestEvent;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DataConverterTest {

    @Test
    public void test_toString() {
        assertEquals(DataConverter.toString(new TestEvent("TEST_VALUE")), TestEvent.serialized("TEST_VALUE"));
    }

    @Test
    public void test_toObject() {
        assertEquals(DataConverter.toObject(TestEvent.serialized("TEST_VALUE"), TestEvent.class), new TestEvent("TEST_VALUE"));
    }
}
