package com.ddd.poc.domain.core.event;

import java.util.Objects;

public final class TestEvent implements DomainEvent {

    private String testField;

    public TestEvent() {
    }

    public TestEvent(String testField) {
        this.testField = testField;
    }

    public String getTestField() {
        return testField;
    }


    public void setTestField(String testField) {
        this.testField = testField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestEvent)) return false;
        TestEvent testEvent = (TestEvent) o;
        return Objects.equals(testField, testEvent.testField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testField);
    }

    public static String serialized(String value) {
        return "{\"testField\":\"" + value + "\"}";
    }

    @Override
    public String toString() {
        return "TestEvent{" +
                "testField='" + testField + '\'' +
                '}';
    }
}