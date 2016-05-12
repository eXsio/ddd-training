package com.ddd.poc.domain.core.dao;

import com.ddd.poc.domain.core.model.CommandEntity;
import com.ddd.poc.domain.core.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface CommandEntityDao extends JpaRepository<CommandEntity, Long> {

    @Query("select c from CommandEntity c order by c.createdAt desc")
    Collection<CommandEntity> findAllOrderByCreatedAt();
}
