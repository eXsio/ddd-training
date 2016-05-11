package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.security.dao.GroupEntityDao;
import com.ddd.poc.domain.security.dao.UserEntityDao;
import com.ddd.poc.domain.security.dto.UserDTO;
import com.google.common.base.Preconditions;
import org.springframework.transaction.annotation.Transactional;

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
        updateEntity(data);
        userEntityDao.save(entity);

        return this;
    }

    protected void updateEntity(UserDTO data) {
        Preconditions.checkNotNull(data);
        entity.setActive(data.isActive());
        entity.setPassword(encryptPassword(data.getPassword()));
        entity.setUsername(data.getUsername());

        addNewGroups(data);
        removeDeletedGroups(data);
    }

    @Transactional
    public UserDM remove() {
        userEntityDao.delete(entity);
        return this;
    }

    private void removeDeletedGroups(UserDTO data) {
        for (GroupEntity groupEntity : entity.getGroups()) {
            if (!data.getGroups().contains(groupEntity.getName())) {
                entity.removeGroup(groupEntity);
            }
        }
    }

    private void addNewGroups(UserDTO data) {
        for (String groupName : data.getGroups()) {
            Optional<GroupEntity> group = groupEntityDao.findByName(groupName);
            group.orElse(groupEntityDao.save(new GroupEntity(groupName)));
            group.ifPresent(entity::addGroup);
        }
    }

    private String encryptPassword(String password) {
        return password;
    }

}
