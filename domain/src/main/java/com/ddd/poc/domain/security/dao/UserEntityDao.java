package com.ddd.poc.domain.security.dao;


import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityDao extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findOneByUsername(String userName);
}
