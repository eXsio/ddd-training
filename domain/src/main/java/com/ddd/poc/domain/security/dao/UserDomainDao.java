package com.ddd.poc.domain.security.dao;

import com.ddd.poc.domain.security.model.UserDM;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDomainDao {

    private final UserEntityDao userEntityDao;

    private final GroupEntityDao groupEntityDao;

    @Autowired
    public UserDomainDao(UserEntityDao userEntityDao, GroupEntityDao groupEntityDao) {
        this.userEntityDao = userEntityDao;
        this.groupEntityDao = groupEntityDao;
    }

    public Optional<UserDM> find(Long id) {
        Optional<UserEntity> userEntity = userEntityDao.findOne(id);
        return userEntity.map(userEntityObj -> Optional.of(new UserDM(userEntityObj, userEntityDao, groupEntityDao))).orElse(Optional.<UserDM>empty());
    }

    public UserDM create() {
        return new UserDM(userEntityDao, groupEntityDao);
    }
}
