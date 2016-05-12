package com.ddd.poc.domain.core.command;

import com.ddd.poc.domain.core.event.DomainEvent;

import java.util.Objects;

public final class TestCommand implements DomainCommand {

    private String testField;

    public TestCommand() {
    }

    public TestCommand(String testField) {
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
        if (!(o instanceof TestCommand)) return false;
        TestCommand testEvent = (TestCommand) o;
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
        return "TestCommand{" +
                "testField='" + testField + '\'' +
                '}';
    }
}