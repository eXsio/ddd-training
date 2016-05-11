package com.ddd.poc.domain.core.ex;

public class EventClassNotFoundRuntimeException extends RuntimeException {

    public EventClassNotFoundRuntimeException(String msg) {
        super(msg);
    }
}
