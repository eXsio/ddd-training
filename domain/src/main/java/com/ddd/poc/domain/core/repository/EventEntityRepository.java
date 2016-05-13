package com.ddd.poc.domain.core.repository;

import com.ddd.poc.domain.core.model.EventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;

public interface EventEntityRepository extends BaseEntityRepository<EventEntity>, Repository<EventEntity, Long> {

    @Query("select e from EventEntity e order by e.createdAt asc")
    Collection<EventEntity> findAllOrderByCreatedAt();
}
