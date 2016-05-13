package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.core.model.BaseAggregate;
import org.springframework.transaction.annotation.Transactional;

public class GroupDM extends BaseAggregate<GroupEntity> {


    public GroupDM() {
        this(new GroupEntity());
    }

    public GroupDM(GroupEntity entity) {
        super(entity);
    }

    @Transactional
    public GroupDM update(String name) {
        entity.setName(name);
        return this;
    }

    @Override
    protected GroupEntity getEntity() {
        return entity;
    }
}
