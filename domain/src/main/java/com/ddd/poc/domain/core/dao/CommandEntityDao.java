package com.ddd.poc.domain.core.dao;

import com.ddd.poc.domain.core.model.CommandEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

public interface CommandEntityDao extends DomainDao<CommandEntity>, Repository<CommandEntity, Long> {

    @Query("select c from CommandEntity c order by c.createdAt desc")
    Collection<CommandEntity> findAllOrderByCreatedAt();

    Optional<CommandEntity> findOneByUuid(String uuid);
}
