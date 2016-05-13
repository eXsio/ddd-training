package com.ddd.poc.domain.core.ex;

public class CommandStoreRuntimeException extends RuntimeException {

    public CommandStoreRuntimeException(String msg, Throwable t) {
        super(msg, t);
    }
}
