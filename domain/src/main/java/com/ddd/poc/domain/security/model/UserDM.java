package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.core.model.BaseAggregate;
import com.ddd.poc.domain.security.dto.UserDTO;
import com.ddd.poc.domain.security.repository.GroupEntityRepository;
import com.ddd.poc.domain.security.repository.UserEntityRepository;
import com.google.common.base.Preconditions;
import org.springframework.transaction.annotation.Transactional;

public class UserDM extends BaseAggregate<UserEntity> {

    private final UserEntityRepository userEntityRepository;

    private final GroupEntityRepository groupEntityRepository;

    public UserDM(UserEntityRepository userEntityRepository, GroupEntityRepository groupEntityRepository) {
        this(new UserEntity(), userEntityRepository, groupEntityRepository);
    }

    public UserDM(UserEntity entity, UserEntityRepository userEntityRepository, GroupEntityRepository groupEntityRepository) {
        super(entity);
        Preconditions.checkNotNull(userEntityRepository);
        Preconditions.checkNotNull(groupEntityRepository);

        this.userEntityRepository = userEntityRepository;
        this.groupEntityRepository = groupEntityRepository;
    }

    @Transactional
    public UserDM save(UserDTO data) {
        Preconditions.checkNotNull(data);
        entity.setActive(data.isActive());
        entity.setPassword(encryptPassword(data.getPassword()));
        entity.setUsername(data.getUsername());
        userEntityRepository.save(entity);

        return this;
    }

    @Transactional
    public UserDM delete() {
        userEntityRepository.delete(entity);
        return this;
    }

    public UserDM joinGroup(Long groupId) {
        groupEntityRepository.findOne(groupId).ifPresent(groupEntity -> {
            entity.addGroup(groupEntity);
            userEntityRepository.save(entity);
        });
        return this;
    }

    public UserDM leaveGroup(Long groupId) {
        groupEntityRepository.findOne(groupId).ifPresent(groupEntity -> {
            entity.removeGroup(groupEntity);
            userEntityRepository.save(entity);
        });
        return this;
    }


    private String encryptPassword(String password) {
        return password;
    }

}
