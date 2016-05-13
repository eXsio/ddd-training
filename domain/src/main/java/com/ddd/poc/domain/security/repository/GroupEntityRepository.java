package com.ddd.poc.domain.security.repository;

import com.ddd.poc.domain.core.repository.BaseEntityRepository;
import com.ddd.poc.domain.security.model.GroupEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface GroupEntityRepository extends BaseEntityRepository<GroupEntity>, Repository<GroupEntity, Long> {

    Optional<GroupEntity> findOneByName(String name);
}
