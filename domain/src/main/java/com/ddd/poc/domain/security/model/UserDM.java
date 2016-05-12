package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.security.dao.GroupEntityDao;
import com.ddd.poc.domain.security.dao.UserEntityDao;
import com.ddd.poc.domain.security.dto.UserDTO;
import com.google.common.base.Preconditions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Optional;

public class UserDM {

    private final UserEntity entity;

    private final UserEntityDao userEntityDao;

    private final GroupEntityDao groupEntityDao;

    public UserDM(UserEntityDao userEntityDao, GroupEntityDao groupEntityDao) {
        this(new UserEntity(), userEntityDao, groupEntityDao);
    }

    public UserDM(UserEntity entity, UserEntityDao userEntityDao, GroupEntityDao groupEntityDao) {
        Preconditions.checkNotNull(entity);
        Preconditions.checkNotNull(userEntityDao);
        Preconditions.checkNotNull(groupEntityDao);

        this.entity = entity;
        this.userEntityDao = userEntityDao;
        this.groupEntityDao = groupEntityDao;
    }

    @Transactional
    public UserDM save(UserDTO data) {
        Preconditions.checkNotNull(data);
        entity.setActive(data.isActive());
        entity.setPassword(encryptPassword(data.getPassword()));
        entity.setUsername(data.getUsername());
        userEntityDao.save(entity);

        return this;
    }

    @Transactional
    public UserDM delete() {
        userEntityDao.delete(entity);
        return this;
    }

    public UserDM joinGroup(Long groupId) {
        GroupEntity groupEntity = groupEntityDao.findOne(groupId);
        entity.addGroup(groupEntity);
        userEntityDao.save(entity);
        return this;
    }

    public UserDM leaveGroup(Long groupId) {
        GroupEntity groupEntity = groupEntityDao.findOne(groupId);
        entity.removeGroup(groupEntity);
        userEntityDao.save(entity);
        return this;
    }


    private String encryptPassword(String password) {
        return password;
    }

}
