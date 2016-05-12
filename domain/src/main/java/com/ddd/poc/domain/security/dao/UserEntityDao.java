package com.ddd.poc.domain.security.dao;


import com.ddd.poc.domain.core.dao.BaseEntityDao;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserEntityDao extends BaseEntityDao<UserEntity>, Repository<UserEntity, Long> {

    Optional<UserEntity> findOneByUsername(String userName);
}
