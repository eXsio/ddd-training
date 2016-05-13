package com.ddd.poc.domain.security.repository;


import com.ddd.poc.domain.core.repository.BaseEntityRepository;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserEntityRepository extends BaseEntityRepository<UserEntity>, Repository<UserEntity, Long> {

    Optional<UserEntity> findOneByUsername(String userName);
}
