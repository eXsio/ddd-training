package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.core.model.BaseAggregate;
import org.springframework.transaction.annotation.Transactional;

public class Group extends BaseAggregate<GroupEntity> {


    public Group() {
        this(new GroupEntity());
    }

    public Group(GroupEntity entity) {
        super(entity);
    }

    public Group update(String name) {
        entity.setName(name);
        return this;
    }

    @Override
    protected GroupEntity getEntity() {
        return entity;
    }
}
