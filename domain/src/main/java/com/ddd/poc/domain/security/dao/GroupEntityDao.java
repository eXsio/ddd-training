package com.ddd.poc.domain.security.dao;

import com.ddd.poc.domain.core.dao.BaseEntityDao;
import com.ddd.poc.domain.security.model.GroupEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface GroupEntityDao extends BaseEntityDao<GroupEntity>, Repository<GroupEntity, Long> {

    Optional<GroupEntity> findOneByName(String name);
}
