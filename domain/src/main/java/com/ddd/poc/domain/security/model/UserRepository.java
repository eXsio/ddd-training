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

    public Optional<User> find(Long id) {
        Optional<UserEntity> userEntity = userDao.findOne(id);
        return userEntity.map(userEntityObj -> Optional.of(new User(userEntityObj, groupDao))).orElse(Optional.<User>empty());
    }

    public User create() {
        return new User(groupDao);
    }

    public User save(User user) {
        return new User(userDao.save(user.getEntity()), groupDao);
    }

    public void delete(User user) {
        userDao.delete(user.getEntity());
    }
}
