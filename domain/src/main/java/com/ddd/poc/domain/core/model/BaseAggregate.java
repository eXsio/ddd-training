package com.ddd.poc.domain.core.model;

import com.google.common.base.Preconditions;

public abstract class BaseAggregate<T> {

    protected final T entity;

    protected BaseAggregate(T entity) {
        Preconditions.checkNotNull(entity);
        this.entity = entity;
    }

    protected abstract T getEntity();
}
