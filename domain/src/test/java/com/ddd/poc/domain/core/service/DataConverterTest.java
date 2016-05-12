package com.ddd.poc.domain.core.service;

import com.ddd.poc.domain.core.event.TestEvent;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DataConverterTest {

    @Test
    public void test_toString() {
        assertTrue(DataConverter.toString(new TestEvent("TEST_VALUE")).contains("TEST_VALUE"));
    }

    @Test
    public void test_toObject() {
        assertEquals(DataConverter.toObject(TestEvent.serialized("TEST_VALUE"), TestEvent.class), new TestEvent("TEST_VALUE"));
    }
}
