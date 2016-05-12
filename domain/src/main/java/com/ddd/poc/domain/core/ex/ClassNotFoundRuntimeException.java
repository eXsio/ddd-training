package com.ddd.poc.domain.core.ex;

public class ClassNotFoundRuntimeException extends RuntimeException {

    public ClassNotFoundRuntimeException(String msg) {
        super(msg);
    }
}
