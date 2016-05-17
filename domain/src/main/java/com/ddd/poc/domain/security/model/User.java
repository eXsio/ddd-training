package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.core.model.BaseAggregate;
import com.ddd.poc.domain.security.dao.GroupDao;
import com.ddd.poc.domain.security.dto.UserDTO;
import com.google.common.base.Preconditions;

public class User extends BaseAggregate<UserEntity> {

    private final GroupDao groupDao;

    public User(GroupDao groupDao) {
        this(new UserEntity(), groupDao);
    }

    public User(UserEntity entity, GroupDao groupDao) {
        super(entity);
        Preconditions.checkNotNull(groupDao);
        this.groupDao = groupDao;
    }

    public User update(UserDTO data) {
        Preconditions.checkNotNull(data);
        entity.setActive(data.isActive());
        entity.setPassword(encryptPassword(data.getPassword()));
        entity.setUsername(data.getUsername());

        return this;
    }

    public User joinGroup(Long groupId) {
        groupDao.findOne(groupId).ifPresent(entity::addGroup);
        return this;
    }

    public User leaveGroup(Long groupId) {
        groupDao.findOne(groupId).ifPresent(entity::removeGroup);
        return this;
    }

    private String encryptPassword(String password) {
        return password;
    }

    @Override
    protected UserEntity getEntity() {
        return entity;
    }
}
