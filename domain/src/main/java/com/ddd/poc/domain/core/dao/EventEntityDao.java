package com.ddd.poc.domain.core.dao;

import com.ddd.poc.domain.core.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EventEntityDao extends JpaRepository<EventEntity, Long> {

    @Query("select e from EventEntity e order by e.createdAt desc")
    Collection<EventEntity> findAllOrderByCreatedAt();
}
