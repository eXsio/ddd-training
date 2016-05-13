package com.ddd.poc.domain.security.dao;


import com.ddd.poc.domain.core.dao.BaseDao;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserDao extends BaseDao<UserEntity>, Repository<UserEntity, Long> {

    Optional<UserEntity> findOneByUsername(String userName);
}
