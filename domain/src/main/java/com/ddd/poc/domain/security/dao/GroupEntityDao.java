package com.ddd.poc.domain.security.dao;

import com.ddd.poc.domain.core.dao.DomainDao;
import com.ddd.poc.domain.security.model.GroupEntity;
import com.ddd.poc.domain.security.model.UserEntity;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface GroupEntityDao extends DomainDao<GroupEntity>, Repository<GroupEntity, Long> {

    Optional<GroupEntity> findOneByName(String name);
}
