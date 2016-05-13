package com.ddd.poc.domain.security.dao;

import com.ddd.poc.domain.core.dao.BaseDao;
import com.ddd.poc.domain.security.model.GroupEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface GroupDao extends BaseDao<GroupEntity>, Repository<GroupEntity, Long> {

    Optional<GroupEntity> findOneByName(String name);
}
