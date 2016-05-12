package com.ddd.poc.domain.core.dao;

import com.ddd.poc.domain.core.model.EventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;

public interface EventEntityDao extends DomainDao<EventEntity>, Repository<EventEntity, Long> {

    @Query("select e from EventEntity e order by e.createdAt desc")
    Collection<EventEntity> findAllOrderByCreatedAt();
}
