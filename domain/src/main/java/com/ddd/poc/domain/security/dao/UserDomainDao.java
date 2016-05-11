package com.ddd.poc.domain.security.dao;

import com.ddd.poc.domain.security.model.UserDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDomainDao {

    private final UserEntityDao userEntityDao;

    private final GroupEntityDao groupEntityDao;

    @Autowired
    public UserDomainDao(UserEntityDao userEntityDao, GroupEntityDao groupEntityDao) {
        this.userEntityDao = userEntityDao;
        this.groupEntityDao = groupEntityDao;
    }

    public Collection<UserDM> find(Collection<Long> ids) {
        return userEntityDao.findAll(ids).stream().map(userEntity -> new UserDM(userEntity, userEntityDao, groupEntityDao)).collect(Collectors.toList());
    }

    public UserDM find(Long id) {
        return new UserDM(userEntityDao.findOne(id), userEntityDao, groupEntityDao);
    }

    public UserDM create() {
        return new UserDM(userEntityDao, groupEntityDao);
    }
}
