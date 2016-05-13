package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.core.model.BaseAggregate;
import com.ddd.poc.domain.security.dto.UserDTO;
import com.ddd.poc.domain.security.dao.GroupDao;
import com.ddd.poc.domain.security.dao.UserDao;
import com.google.common.base.Preconditions;
import org.springframework.transaction.annotation.Transactional;

public class UserDM extends BaseAggregate<UserEntity> {

    private final GroupDao groupDao;

    public UserDM(GroupDao groupDao) {
        this(new UserEntity(), groupDao);
    }

    public UserDM(UserEntity entity, GroupDao groupDao) {
        super(entity);
        Preconditions.checkNotNull(groupDao);
        this.groupDao = groupDao;
    }

    public UserDM update(UserDTO data) {
        Preconditions.checkNotNull(data);
        entity.setActive(data.isActive());
        entity.setPassword(encryptPassword(data.getPassword()));
        entity.setUsername(data.getUsername());

        return this;
    }

    public UserDM joinGroup(Long groupId) {
        groupDao.findOne(groupId).ifPresent(groupEntity -> {
            entity.addGroup(groupEntity);
        });
        return this;
    }

    public UserDM leaveGroup(Long groupId) {
        groupDao.findOne(groupId).ifPresent(groupEntity -> {
            entity.removeGroup(groupEntity);
        });
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
