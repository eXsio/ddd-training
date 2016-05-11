package com.ddd.poc.domain.core.dao;

import com.ddd.poc.domain.core.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface EventEntityDao extends JpaRepository<EventEntity, Long> {

    Collection<EventEntity> findAllOrderByCreatedAt();
}
