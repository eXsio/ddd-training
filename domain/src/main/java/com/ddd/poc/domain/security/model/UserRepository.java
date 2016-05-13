package com.ddd.poc.domain.security.model;

import com.ddd.poc.domain.security.dao.GroupDao;
import com.ddd.poc.domain.security.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepository {

    private final UserDao userDao;

    private final GroupDao groupDao;

    @Autowired
    public UserRepository(UserDao userDao, GroupDao groupDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
    }

    public Optional<UserDM> find(Long id) {
        Optional<UserEntity> userEntity = userDao.findOne(id);
        return userEntity.map(userEntityObj -> Optional.of(new UserDM(userEntityObj, groupDao))).orElse(Optional.<UserDM>empty());
    }

    public UserDM create() {
        return new UserDM(groupDao);
    }

    public UserDM save(UserDM userDM) {
        return new UserDM(userDao.save(userDM.getEntity()), groupDao);
    }

    public void delete(UserDM userDM) {
        userDao.delete(userDM.getEntity());
    }
}
